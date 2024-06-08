package modelance.backend.service.account;

import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import modelance.backend.dto.JobDTO;
import modelance.backend.model.work.JobModel;

@Service
public class EmployerJobManagementService {
    private Firestore firestore;

    public EmployerJobManagementService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public String createNewJob(JobDTO jobDTO) throws ExecutionException, InterruptedException{
        JobModel jobModel = new JobModel();
        jobModel.setTitle(jobDTO.getTitle());
        jobModel.setImageURL(jobDTO.getImageURL());
        jobModel.setJobDesctription(jobDTO.getJobDescription());
        jobModel.setPayment(jobDTO.getPayment());
        jobModel.setStartDate(jobDTO.getStartDate());
        jobModel.setEndDate(jobDTO.getEndDate());

        DocumentReference employerRef = firestore.collection("Employer").document(jobDTO.getEmployer().getId());
        jobModel.setEmployer(employerRef);

        DocumentReference categoryRef = firestore.collection("JobCategories").document(jobDTO.getCategory());
        jobModel.setCategory(categoryRef);

        DocumentReference statusRef = firestore.collection("JobStatus").document("1");
        jobModel.setStatus(statusRef);

        ApiFuture<DocumentReference> addQuery = firestore.collection("Job").add(jobModel);
        DocumentReference docRef = addQuery.get();
        docRef.update("id", docRef.getId());
        return docRef.getId();
    }

}
