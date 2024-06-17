package modelance.backend.service.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import modelance.backend.firebasedto.work.JobDTO;
import modelance.backend.model.JobModel;

@Service
public class ModelJobService {
    private Firestore firestore;
    private final ObjectMapper objectMapper;

    public ModelJobService(ObjectMapper objectMapper) {
        this.firestore = FirestoreClient.getFirestore();
        this.objectMapper = objectMapper;
    }

    public ArrayList<JobModel> getAllJobs() throws ExecutionException, InterruptedException {
        ArrayList<JobModel> jobList = new ArrayList<>();
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

    public List<JobModel> getAppliedJobs(Authentication authentication)
            throws InterruptedException, ExecutionException {
        List<JobModel> jobList = new ArrayList<>();
        String userId = authentication.getName();
        QuerySnapshot future = firestore.collection("Job").whereArrayContains("applicants", userId).get().get();
        List<QueryDocumentSnapshot> documents = future.getDocuments();
        for (QueryDocumentSnapshot doc : documents) {
            JobDTO jobDTO = doc.toObject(JobDTO.class);
            JobModel jobModel = objectMapper.convertValue(jobDTO, JobModel.class);
            jobModel.setApplicants(null);
            jobList.add(jobModel);
        }
        return jobList;
    }

    public boolean applyForJob(Authentication authentication, String jobId) {
        String userId = authentication.getName();
        firestore.collection("Job").document(jobId).update("applicants", FieldValue.arrayUnion(userId));
        return true;
    }

    public boolean unapplyJob(Authentication authentication, String jobId) {
        String userId = authentication.getName();
        firestore.collection("Job").document(jobId).update("applicants", FieldValue.arrayRemove(userId));
        return true;
    }
}