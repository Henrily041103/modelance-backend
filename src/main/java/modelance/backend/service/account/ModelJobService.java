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
import modelance.backend.firebasedto.work.JobDTO;
import modelance.backend.model.EmployerModel;
import modelance.backend.model.JobModel;

@Service
public class ModelJobService {
    private Firestore firestore;
    private final ObjectMapper objectMapper;

    public ModelJobService(ObjectMapper objectMapper) {
        this.firestore = FirestoreClient.getFirestore();
        this.objectMapper = objectMapper;
    }

    public ArrayList<JobModel> getJobListService() throws ExecutionException, InterruptedException {
        ArrayList<JobModel> jobList = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection("Job").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot doc : documents) {
            JobDTO jobDTO = doc.toObject(JobDTO.class);
            // JobDTO
            JobModel jobModel = new JobModel();
            jobModel.setId(jobDTO.getId());
            jobModel.setTitle(jobDTO.getTitle());
            jobModel.setImageURL(jobDTO.getImageURL());
            jobModel.setCategory(jobDTO.getCategory().getCategoryName());
            jobModel.setPayment(jobDTO.getPayment());
            jobList.add(jobModel);
        }
        return jobList;
    }

    @SuppressWarnings("null")
    public JobModel getJobsById(String id) throws ExecutionException, InterruptedException {
        JobModel jobModel = new JobModel();
        DocumentReference docRef = firestore.collection("Job").document(id.trim());
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot docSnap = future.get();
        if (docSnap.exists()) {
            JobDTO jobDTO = docSnap.toObject(JobDTO.class);
            jobModel.setId(jobDTO.getId());
            jobModel.setTitle(jobDTO.getTitle());
            jobModel.setPayment(jobDTO.getPayment());
            jobModel.setImageURL(jobDTO.getImageURL());
            jobModel.setStartDate(jobDTO.getStartDate());
            jobModel.setEndDate(jobDTO.getEndDate());
            jobModel.setJobDescription(jobDTO.getJobDescription());

            // Get category
            jobModel.setCategory(jobDTO.getCategory().getCategoryName());

            // Get employer
            EmployerModel empModel = objectMapper.convertValue(jobDTO.getEmployer(), EmployerModel.class);
            jobModel.setEmployer(empModel);

            // Get status
            jobModel.setStatus(jobDTO.getStatus().getStatusName());
        }
        return jobModel;
    }
}