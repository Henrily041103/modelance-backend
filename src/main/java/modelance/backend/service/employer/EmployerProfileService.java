package modelance.backend.service.employer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import modelance.backend.firebasedto.account.AccountDTO;
import modelance.backend.firebasedto.work.ReviewDTO;
import modelance.backend.model.AccountModel;
import modelance.backend.model.ReviewModel;
import modelance.backend.service.account.NoAccountExistsException;

@Service
public class EmployerProfileService {
    private Firestore firestore;
    private final ObjectMapper objectMapper;

    public EmployerProfileService(ObjectMapper objectMapper) {
        this.firestore = FirestoreClient.getFirestore();
        this.objectMapper = objectMapper;
    }

    public boolean updateEmployerProfile(Map<String, Object> updates, Authentication authentication)
            throws InterruptedException, ExecutionException, NoAccountExistsException {
        String userId = authentication.getName();
        boolean result = false;

        DocumentSnapshot documentSnap = firestore.collection("Employer").document(userId).get().get();
        if (documentSnap.exists()) {
            try {
                Map<String, Object> checkedUpdates = new HashMap<>();
                for (Map.Entry<String, Object> entry : updates.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (value != null) {
                        checkedUpdates.put(key, value);
                    }
                }

                firestore.collection("Employer").document(userId).set(checkedUpdates);
                result = true;

            } catch (ClassCastException | NullPointerException e) {
                throw new NoAccountExistsException();
            }
        }

        return result;
    }

    public List<ReviewModel> getAllReview(Authentication authentication)
            throws InterruptedException, ExecutionException {
        List<ReviewModel> reviewList = new ArrayList<>();
        String userId = authentication.getName();

        // Get all reviews sent by given reviewer
        ApiFuture<QuerySnapshot> future = firestore.collection("Review").whereEqualTo("reviewer.id", userId).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot doc : documents) {
            ReviewDTO reviewDTO = doc.toObject(ReviewDTO.class);

            // Get reviewee
            AccountDTO revieweeDTO = objectMapper.convertValue(reviewDTO.getReviewee(), AccountDTO.class);

            AccountModel employer = new AccountModel();
            employer.setId(revieweeDTO.getId());
            employer.setFullName(revieweeDTO.getFullName());
            employer.setAvatar(revieweeDTO.getAvatar());

            // Review DTO
            ReviewModel review = new ReviewModel();
            review.setContent(reviewDTO.getContent());
            review.setContract(reviewDTO.getContract().getId());
            review.setDatetime(reviewDTO.getDatetime());
            review.setRating(reviewDTO.getRating());
            review.setReviewer(employer);
            review.setReviewee(employer);
            reviewList.add(review);
        }
        return reviewList;
    }
}
