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
public class EmployerJobManagementService {
    private Firestore firestore;
    private ObjectMapper objectMapper;

    public EmployerJobManagementService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.firestore = FirestoreClient.getFirestore();
    }

    public String createNewJob(JobModel jobModel) throws ExecutionException, InterruptedException {
        JobDTO jobDTO = objectMapper.convertValue(jobModel, JobDTO.class);

        ApiFuture<DocumentReference> addQuery = firestore.collection("Job").add(jobDTO);
        DocumentReference docRef = addQuery.get();
        docRef.update("id", docRef.getId());
        return docRef.getId();
    }

    public ArrayList<JobModel> getAllPostedJob(String id) throws InterruptedException, ExecutionException {
        ArrayList<JobModel> jobList = new ArrayList<>();
        // Get employer reference
        DocumentReference employerRef = firestore.collection("Employer").document(id);

        // Get all job with given employerRef
        ApiFuture<QuerySnapshot> future = firestore.collection("Job").whereEqualTo("employer", employerRef).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot doc : documents) {
            JobDTO jobDTO = doc.toObject(JobDTO.class);


            // JobDTO
            JobModel jobModel = new JobModel();
            jobModel.setId(doc.getId());
            jobModel.setTitle(jobDTO.getTitle());
            jobModel.setImageURL(jobDTO.getImageURL());
            jobModel.setCategory(jobDTO.getCategory().getCategoryName());
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
            if (jobDTO == null)
                return job;
            job.setId(jobDTO.getId());
            job.setTitle(jobDTO.getTitle());
            job.setPayment(jobDTO.getPayment());
            job.setImageURL(jobDTO.getImageURL());
            job.setStartDate(jobDTO.getStartDate());
            job.setEndDate(jobDTO.getEndDate());
            job.setJobDescription(jobDTO.getJobDescription());

            // Get category
            job.setCategory(jobDTO.getCategory().getCategoryName());

            // Get employer
            EmployerModel empModel = objectMapper.convertValue(jobDTO.getEmployer(), EmployerModel.class);
            job.setEmployer(empModel);

            // Get status
            job.setStatus(jobDTO.getStatus().getStatusName());
        }
        return job;
    }

    public String updateJobStatus(String id, String statusid) throws InterruptedException, ExecutionException {
        DocumentReference statusRef = firestore.collection("JobStatus").document(statusid);
        DocumentReference jobRef = firestore.collection("Job").document(id);
        jobRef.update("status", statusRef);
        return "";
    }

}
