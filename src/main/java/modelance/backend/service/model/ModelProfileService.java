package modelance.backend.service.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;

import modelance.backend.firebasedto.account.AccountDTO;
import modelance.backend.firebasedto.account.ModelDTO;
import modelance.backend.firebasedto.account.PortfolioDTO;
import modelance.backend.firebasedto.work.ReviewDTO;
import modelance.backend.model.AccountModel;
import modelance.backend.model.IndustryModel;
import modelance.backend.model.LocationModel;
import modelance.backend.model.ModelBodyIndexModel;
import modelance.backend.model.ReviewModel;
import modelance.backend.model.SocialMediaModel;

@Service
public class ModelProfileService {
    private final Firestore firestore;
    private final ObjectMapper objectMapper;
    private StorageClient storageClient;

    public ModelProfileService(ObjectMapper objectMapper) {
        this.firestore = FirestoreClient.getFirestore();
        this.storageClient = StorageClient.getInstance();
        this.objectMapper = objectMapper;
    }

    public List<String> updateCompCard(MultipartFile[] files, Authentication authentication)
            throws IOException, InterruptedException, ExecutionException {
        String userId = authentication.getName();
        Bucket bucket = storageClient.bucket();
        List<String> urlList = new ArrayList<>();

        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename();
            String filetype = filename.substring(filename.lastIndexOf(".") + 1);
            String filepath = "comp_card/" + userId + "/" + filename + "." + filetype;
            String contentType = "image/" + filetype;

            bucket.create(filepath, file.getInputStream(), contentType);

            String url = "https://firebasestorage.googleapis.com/v0/b/modelance-84abf.appspot.com/o/"
                    + "comp_card%2F"
                    + userId + "%2F"
                    + filename
                    + "." + filetype
                    + "?alt=media";
            urlList.add(url);
        }

        DocumentReference compCardRef = firestore.collection("Model").document(userId);
        ModelDTO model = compCardRef.get().get().toObject(ModelDTO.class);
        if (model != null) {
            model.addToCompCardList(urlList);
            compCardRef.update("compCard", model.getCompCard());
        }

        return urlList;
    }

    public List<String> updatePortfolio(MultipartFile[] files, Authentication authentication,
            String title)
            throws IOException, InterruptedException, ExecutionException {
        String userId = authentication.getName();
        Bucket bucket = storageClient.bucket();
        List<String> urlList = new ArrayList<>();
        UUID uuid = UUID.nameUUIDFromBytes((userId + title).getBytes());
        String jobId = uuid.toString().toString().replace('-', '_');

        // add file
        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename();
            String filetype = filename.substring(filename.lastIndexOf(".") + 1);
            String filepath = "portfolio/" + userId + "/" + jobId + "/" + filename + "." + filetype;
            String contentType = "image/" + filetype;

            bucket.create(filepath, file.getInputStream(), contentType);

            String url = "https://firebasestorage.googleapis.com/v0/b/modelance-84abf.appspot.com/o/"
                    + "portfolio%2F"
                    + userId + "%2F"
                    + jobId + "%2F"
                    + filename
                    + "." + filetype
                    + "?alt=media";
            urlList.add(url);
        }

        // change data
        DocumentReference compCardRef = firestore.collection("Model").document(userId);
        ModelDTO model = compCardRef.get().get().toObject(ModelDTO.class);
        List<PortfolioDTO> portfolioList = new ArrayList<>();

        if (model != null) {
            if (model.getPortfolio() != null)
                portfolioList.addAll(model.getPortfolio());
            portfolioList.add(new PortfolioDTO(jobId, urlList, title));

            compCardRef.update("portfolio", portfolioList);
        }

        return urlList;
    }

    public List<SocialMediaModel> updateSocialMedia(Authentication authentication, List<SocialMediaModel> socialMedia)
            throws InterruptedException, ExecutionException {
        List<SocialMediaModel> result = null;
        String userId = authentication.getName();

        firestore.collection("Model").document(userId).update("socialMedia", socialMedia);
        result = socialMedia;

        return result;
    }

    public boolean updateModelWorkInfo(
            Authentication authentication,
            String description,
            ModelBodyIndexModel bodyModel,
            IndustryModel industry,
            LocationModel location,
            String eyeColor,
            String hairColor,
            Float hourlyRate)
            throws InterruptedException, ExecutionException {
        boolean result = false;
        Map<String, Object> updater = new HashMap<>();
        String userId = authentication.getName();

        if (bodyModel != null) {
            Map<String, Object> bodyMap = objectMapper.convertValue(
                    bodyModel,
                    new TypeReference<Map<String, Object>>() {
                    });
            updater = new HashMap<>(bodyMap);
        }
        if (industry != null) {
            updater.put("industry", industry);
            result = true;
        }
        if (location != null) {
            updater.put("location", location);
            result = true;
        }
        if (description != null) {
            updater.put("description", description);
            result = true;
        }
        if (eyeColor != null) {
            updater.put("eyeColor", eyeColor);
            result = true;
        }
        if (hairColor != null) {
            updater.put("hairColor", hairColor);
            result = true;
        }
        if (hourlyRate != null) {
            updater.put("hourlyRate", hourlyRate);
            result = true;
        }
        firestore.collection("Model").document(userId).update(updater);
        return result;
    }

    public List<String> updateCategory(Authentication authentication, List<String> category)
            throws InterruptedException, ExecutionException {
        String userId = authentication.getName();

        for (String modelCategory : category) {
            DocumentReference categoryRef = firestore.collection("Category").document(modelCategory);
            DocumentSnapshot categoryDoc = categoryRef.get().get();
            if (categoryDoc.exists()) {
                categoryRef.update("model", FieldValue.arrayUnion(userId));
            } else {
                Map<String, List<String>> addMap = new HashMap<>();
                List<String> models = Arrays.asList(new String[] { userId });
                addMap.put("model", models);
                categoryRef.set(addMap);
            }
        }
        firestore.collection("Model").document(userId).update("category", FieldValue.arrayUnion(category.toArray()));

        return category;
    }

    public ArrayList<ReviewModel> getAllReview(Authentication authentication)
            throws InterruptedException, ExecutionException {
        ArrayList<ReviewModel> reviewList = new ArrayList<>();
        String userId = authentication.getName();

        // Get all reviews sent by given reviewer
        ApiFuture<QuerySnapshot> future = firestore.collection("Review").whereEqualTo("reviewer.id", userId).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot doc : documents) {
            ReviewDTO reviewDTO = doc.toObject(ReviewDTO.class);

            // Get reviewee
            AccountDTO reviewee = objectMapper.convertValue(reviewDTO.getReviewee(), AccountDTO.class);

            AccountModel model = new AccountModel();
            model.setId(reviewee.getId());
            model.setFullName(reviewee.getFullName());
            model.setAvatar(reviewee.getAvatar());

            // Review DTO
            ReviewModel review = new ReviewModel();
            review.setContent(reviewDTO.getContent());
            review.setContract(reviewDTO.getContract().getId());
            review.setDatetime(reviewDTO.getDatetime());
            review.setRating(reviewDTO.getRating());
            review.setReviewer(model);
            review.setReviewee(model);
            reviewList.add(review);
        }
        return reviewList;
    }
}
