package modelance.backend.service.account;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import modelance.backend.dto.EmployerDTO;
import modelance.backend.dto.ModelDTO;
import modelance.backend.dto.ReviewDTO;
import modelance.backend.model.account.AccountModel;
import modelance.backend.model.account.EmployerModel;
import modelance.backend.model.work.ReviewModel;

@Service
public class ProfileService {
    private Firestore firestore;

    public ProfileService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public String updateEmployerProfile(EmployerModel employerModel) throws InterruptedException, ExecutionException {
        ApiFuture<WriteResult> employerApiFuture = firestore.collection("Employer").document(employerModel.getId())
                .set(employerModel);
        ApiFuture<WriteResult> accountApiFuture = firestore.collection("Account").document(employerModel.getId())
                .set(employerModel);
        return employerApiFuture.get().getUpdateTime().toString() + accountApiFuture.get().getUpdateTime().toString();
    }

    public ArrayList<ReviewDTO> getAllReview(String id) throws InterruptedException, ExecutionException {
        ArrayList<ReviewDTO> reviewList = new ArrayList<>();

        // Get reviewer
        DocumentReference employerRef = firestore.collection("Account").document(id);
        DocumentSnapshot empSnap = employerRef.get().get();
        AccountModel employerModel = empSnap.toObject(AccountModel.class);
        EmployerDTO empDTO = new EmployerDTO();
        empDTO.setId(employerRef.getId());
        empDTO.setFullName(employerModel.getFullName());
        empDTO.setAvatar(employerModel.getAvatar());

        // Get all reviews sent by given reviewer
        ApiFuture<QuerySnapshot> future = firestore.collection("Review").whereEqualTo("reviewer", employerRef).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot doc : documents) {
            ReviewModel reviewModel = doc.toObject(ReviewModel.class);

            // Get contract ref
            DocumentReference contractRef = reviewModel.getContract();
            
            // Get reviewee
            DocumentReference revieweeRef = reviewModel.getReviewee();
            DocumentSnapshot revieweeSnap = revieweeRef.get().get();
            AccountModel revieweeModel = revieweeSnap.toObject(AccountModel.class);
            ModelDTO revieweeDTO = new ModelDTO();
            revieweeDTO.setId(revieweeRef.getId());
            revieweeDTO.setFullName(revieweeModel.getFullName());
            revieweeDTO.setAvatar(revieweeModel.getAvatar());

            //Review DTO
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setContent(reviewModel.getContent());
            reviewDTO.setContract(contractRef.getId());
            reviewDTO.setDatetime(reviewModel.getDatetime());
            reviewDTO.setRating(reviewModel.getRating());
            reviewDTO.setReviewer(empDTO);
            reviewDTO.setReviewee(revieweeDTO);
            reviewList.add(reviewDTO);
        }
        return reviewList;
    }

}
