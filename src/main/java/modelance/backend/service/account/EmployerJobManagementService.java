package modelance.backend.service.account;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import modelance.backend.firebasedto.account.AccountDTO;
import modelance.backend.firebasedto.refdto.PersonDTO;
import modelance.backend.firebasedto.work.JobDTO;
import modelance.backend.model.JobModel;

@Service
public class EmployerJobManagementService {
    private Firestore firestore;
    private ObjectMapper objectMapper;

    public EmployerJobManagementService() {
        this.firestore = FirestoreClient.getFirestore();
        this.objectMapper = new ObjectMapper();
    }

    public String createNewJob(JobModel jobModel) throws ExecutionException, InterruptedException {
        JobDTO jobDTO = objectMapper.convertValue(jobModel, JobDTO.class);
        ApiFuture<DocumentReference> addQuery = firestore.collection("Job").add(jobDTO);
        DocumentReference docRef = addQuery.get();
        docRef.update("id", docRef.getId());
        return docRef.getId();
    }

    public ArrayList<JobModel> getAllPostedJob(String employerID) throws InterruptedException, ExecutionException {
        ArrayList<JobModel> jobList = new ArrayList<>();
        // Get all job with given employerID
        DocumentReference accRef = firestore.collection("Account").document(employerID);
        AccountDTO accDTO = accRef.get().get().toObject(AccountDTO.class);
        PersonDTO acc = new PersonDTO(employerID, accDTO.getFullName());
        ApiFuture<QuerySnapshot> future = firestore.collection("Job").whereEqualTo("employer", acc).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot doc : documents) {
            JobDTO jobDTO = doc.toObject(JobDTO.class);
            JobModel jobModel = objectMapper.convertValue(jobDTO, JobModel.class);
            jobList.add(jobModel);
        }
        return jobList;
    }

    public JobModel getPostedJobDetails(String id) throws InterruptedException, ExecutionException {
        JobModel job = new JobModel();
        DocumentReference docRef = firestore.collection("Job").document(id.trim());
        DocumentSnapshot docSnap = docRef.get().get();
        if (docSnap.exists()) {
            JobDTO jobDTO = docSnap.toObject(JobDTO.class);
            job = objectMapper.convertValue(jobDTO, JobModel.class);
        }
        return job;
    }

}
