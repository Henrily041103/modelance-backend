package modelance.backend.service.account;

import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import modelance.backend.model.account.EmployerModel;

@Service
public class ProfileService {
    private Firestore firestore;
    public ProfileService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public String updateEmployerProfile(EmployerModel employerModel) throws InterruptedException, ExecutionException {
        ApiFuture<WriteResult> employerApiFuture = firestore.collection("Employer").document(employerModel.getId()).set(employerModel);
        ApiFuture<WriteResult> accountApiFuture = firestore.collection("Account").document(employerModel.getId()).set(employerModel);
        return employerApiFuture.get().getUpdateTime().toString() + accountApiFuture.get().getUpdateTime().toString();
    }


}
