package modelance.backend.service.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import modelance.backend.model.ReviewModel;

@Service
public class EmployerProfileService {
    private Firestore firestore;
    private ObjectMapper objectMapper;

    public EmployerProfileService() {
        this.firestore = FirestoreClient.getFirestore();
        this.objectMapper = new ObjectMapper();
    }

    public String updateEmployerProfile(EmployerDTO employerModel) throws InterruptedException, ExecutionException {
        ApiFuture<WriteResult> employerApiFuture = firestore.collection("Employer").document(employerModel.getId())
                .set(employerModel);
        ApiFuture<WriteResult> accountApiFuture = firestore.collection("Account").document(employerModel.getId())
                .set(employerModel);
        return employerApiFuture.get().getUpdateTime().toString() + accountApiFuture.get().getUpdateTime().toString();
    }

    public ArrayList<ReviewModel> getAllReview(String employerID) throws InterruptedException, ExecutionException {
        ArrayList<ReviewModel> reviewList = new ArrayList<>();
        // Get reviewer
        DocumentReference empRef = firestore.collection("Account").document(employerID);
        AccountDTO accDTO = empRef.get().get().toObject(AccountDTO.class);
        Map<String,String> reviewer = new HashMap<>();
        reviewer.put("id", empRef.getId());
        reviewer.put("fullName", accDTO.getFullName());
        // Get all reviews sent by given reviewer
        ApiFuture<QuerySnapshot> future = firestore.collection("Review").whereEqualTo("reviewer", reviewer).get();
        
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot doc : documents) {
            ReviewDTO reviewDTO = doc.toObject(ReviewDTO.class);
            ReviewModel review = objectMapper.convertValue(reviewDTO, ReviewModel.class);
            reviewList.add(review);
        }
        return reviewList;
    }

}
