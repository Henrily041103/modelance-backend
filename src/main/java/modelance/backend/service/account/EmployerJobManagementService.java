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
import com.google.firebase.cloud.FirestoreClient;

import modelance.backend.firebasedto.account.AccountDTO;
import modelance.backend.firebasedto.work.JobCategoriesDTO;
import modelance.backend.firebasedto.work.JobDTO;
import modelance.backend.firebasedto.work.JobStatusDTO;
import modelance.backend.model.EmployerModel;
import modelance.backend.model.JobModel;

@Service
public class EmployerJobManagementService {
    private Firestore firestore;

    public EmployerJobManagementService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public String createNewJob(JobModel jobModel) throws ExecutionException, InterruptedException {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setTitle(jobModel.getTitle());
        jobDTO.setImageURL(jobModel.getImageURL());
        jobDTO.setJobDesctription(jobModel.getJobDescription());
        jobDTO.setPayment(jobModel.getPayment());
        jobDTO.setStartDate(jobModel.getStartDate());
        jobDTO.setEndDate(jobModel.getEndDate());

        DocumentReference employerRef = firestore.collection("Employer").document(jobModel.getEmployer().getId());
        jobDTO.setEmployer(employerRef);

        DocumentReference categoryRef = firestore.collection("JobCategories").document(jobModel.getCategory());
        jobDTO.setCategory(categoryRef);

        DocumentReference statusRef = firestore.collection("JobStatus").document("1");
        jobDTO.setStatus(statusRef);

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

            // Get Job category
            DocumentSnapshot docSnap = jobDTO.getCategory().get().get();
            JobCategoriesDTO categoriesDTO = docSnap.toObject(JobCategoriesDTO.class);

            // JobDTO
            JobModel jobModel = new JobModel();
            jobModel.setId(doc.getId());
            jobModel.setTitle(jobDTO.getTitle());
            jobModel.setImageURL(jobDTO.getImageURL());
            if (categoriesDTO != null) {
                jobModel.setCategory(categoriesDTO.getCategoryName());
            }
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
            DocumentSnapshot cateSnap = jobDTO.getCategory().get().get();
            JobCategoriesDTO categoriesDTO = cateSnap.toObject(JobCategoriesDTO.class);
            job.setCategory(categoriesDTO.getCategoryName());

            // Get employer
            DocumentReference employerRef = jobDTO.getEmployer();
            DocumentReference accountRef = firestore.collection("Account").document(employerRef.getId());
            AccountDTO accountDTO = accountRef.get().get().toObject(AccountDTO.class);
            EmployerModel empModel = new EmployerModel();
            empModel.setId(employerRef.getId());
            empModel.setFullName(accountDTO.getFullName());
            empModel.setAvatar(accountDTO.getAvatar());
            job.setEmployer(empModel);

            // Get status
            DocumentSnapshot statSnap = jobDTO.getStatus().get().get();
            JobStatusDTO statDTO = statSnap.toObject(JobStatusDTO.class);
            job.setStatus(statDTO.getStatusName());
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
