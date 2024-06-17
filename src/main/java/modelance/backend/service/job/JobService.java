package modelance.backend.service.job;

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
import modelance.backend.model.JobModel;

@Service
public class JobService {
    private Firestore firestore;
    private final ObjectMapper objectMapper;

    public JobService(ObjectMapper objectMapper) {
        this.firestore = FirestoreClient.getFirestore();
        this.objectMapper = objectMapper;
    }

    public List<JobModel> getAllJobs() throws ExecutionException, InterruptedException {
        List<JobModel> jobList = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection("Job").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot doc : documents) {
            JobDTO jobDTO = doc.toObject(JobDTO.class);
            JobModel jobModel = objectMapper.convertValue(jobDTO, JobModel.class);
            if (jobModel != null) {
                jobModel.setApplicants(null);
                jobList.add(jobModel);
            }

        }
        return jobList;
    }

    public JobModel getJobsById(String id) throws ExecutionException, InterruptedException {
        JobModel jobModel = null;
        DocumentReference docRef = firestore.collection("Job").document(id.trim());
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot docSnap = future.get();
        if (docSnap.exists()) {
            JobDTO jobDTO = docSnap.toObject(JobDTO.class);
            jobModel = objectMapper.convertValue(jobDTO, JobModel.class);
            jobModel.setApplicants(null);
        }
        return jobModel;
    }
}
