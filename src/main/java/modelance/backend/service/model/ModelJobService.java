package modelance.backend.service.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import modelance.backend.firebasedto.account.AccountDTO;
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

    public JobModel applyForJob(Authentication authentication, String jobId)
            throws InterruptedException, ExecutionException {
        String userId = authentication.getName();
        JobModel model = null;
        AccountDTO account = firestore.collection("Account").document(userId).get().get().toObject(AccountDTO.class);
        if (account != null) {
            DocumentReference docRef = firestore.collection("Job").document(jobId);
            JobDTO jobDTO = docRef.get().get().toObject(JobDTO.class);

            if (jobDTO != null) {
                jobDTO.addApplicants(userId, account.getFullName(), account.getAvatar());
                docRef.update("applicants", jobDTO.getApplicants());
                model = objectMapper.convertValue(jobDTO, JobModel.class);
            }
        }
        return model;
    }

    public JobModel unapplyJob(Authentication authentication, String jobId)
            throws InterruptedException, ExecutionException {
        String userId = authentication.getName();
        JobModel model = null;
        DocumentReference docRef = firestore.collection("Job").document(jobId);
        JobDTO jobDTO = docRef.get().get().toObject(JobDTO.class);

        if (jobDTO != null) {
            jobDTO.removeApplicants(userId);
            docRef.update("applicants", jobDTO.getApplicants());
            model = objectMapper.convertValue(jobDTO, JobModel.class);
        }
        return model;
    }
}