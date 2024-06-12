package modelance.backend.service.account;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import modelance.backend.firebasedto.account.AccountDTO;
import modelance.backend.firebasedto.account.EmployerDTO;
import modelance.backend.firebasedto.work.ReviewDTO;
import modelance.backend.model.EmployerModel;
import modelance.backend.model.ModelModel;
import modelance.backend.model.ReviewModel;

@Service
public class EmployerProfileService {
    private Firestore firestore;
    private final ObjectMapper objectMapper;

    public EmployerProfileService(ObjectMapper objectMapper) {
        this.firestore = FirestoreClient.getFirestore();
        this.objectMapper = objectMapper;
    }

    public String updateEmployerProfile(EmployerDTO employerModel) throws InterruptedException, ExecutionException {
        ApiFuture<WriteResult> employerApiFuture = firestore.collection("Employer").document(employerModel.getId())
                .set(employerModel);
        ApiFuture<WriteResult> accountApiFuture = firestore.collection("Account").document(employerModel.getId())
                .set(employerModel);
        return employerApiFuture.get().getUpdateTime().toString() + accountApiFuture.get().getUpdateTime().toString();
    }

    public ArrayList<ReviewModel> getAllReview(String id) throws InterruptedException, ExecutionException {
        ArrayList<ReviewModel> reviewList = new ArrayList<>();

        // Get reviewer
        DocumentReference empRef = firestore.collection("Account").document(id);
        AccountDTO accDTO = empRef.get().get().toObject(AccountDTO.class);
        EmployerModel employer = objectMapper.convertValue(accDTO, EmployerModel.class);

        // Get all reviews sent by given reviewer
        ApiFuture<QuerySnapshot> future = firestore.collection("Review").whereEqualTo("reviewer", empRef).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot doc : documents) {
            ReviewDTO reviewDTO = doc.toObject(ReviewDTO.class);

            // Get reviewee
            AccountDTO reviewee = objectMapper.convertValue(reviewDTO.getReviewee(), AccountDTO.class);

            ModelModel model = new ModelModel();
            model.setId(reviewee.getId());
            model.setFullName(reviewee.getFullName());
            model.setAvatar(reviewee.getAvatar());

            //Review DTO
            ReviewModel review = new ReviewModel();
            review.setContent(reviewDTO.getContent());
            review.setContract(reviewDTO.getContract().getId());
            review.setDatetime(reviewDTO.getDatetime());
            review.setRating(reviewDTO.getRating());
            review.setReviewer(employer);
            review.setReviewee(model);
            reviewList.add(review);
        }
        return reviewList;
    }

}
