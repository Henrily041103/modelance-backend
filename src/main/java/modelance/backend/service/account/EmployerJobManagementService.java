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
import modelance.backend.dto.EmployerDTO;
import modelance.backend.dto.JobDTO;
import modelance.backend.model.account.AccountModel;
import modelance.backend.model.work.JobCategoriesModel;
import modelance.backend.model.work.JobModel;
import modelance.backend.model.work.JobStatusModel;

@Service
public class EmployerJobManagementService {
    private Firestore firestore;

    public EmployerJobManagementService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public String createNewJob(JobDTO jobDTO) throws ExecutionException, InterruptedException {
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

    public ArrayList<JobDTO> getAllPostedJob(String id) throws InterruptedException, ExecutionException {
        ArrayList<JobDTO> jobList = new ArrayList<>();
        // Get employer reference
        DocumentReference employerRef = firestore.collection("Employer").document(id);

        // Get all job with given employerRef
        ApiFuture<QuerySnapshot> future = firestore.collection("Job").whereEqualTo("employer", employerRef).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot doc : documents) {
            JobModel jobModel = doc.toObject(JobModel.class);

            // Get Job category
            DocumentSnapshot docSnap = jobModel.getCategory().get().get();
            JobCategoriesModel categoriesModel = docSnap.toObject(JobCategoriesModel.class);

            // JobDTO
            JobDTO jobDTO = new JobDTO();
            jobDTO.setId(doc.getId());
            jobDTO.setTitle(jobModel.getTitle());
            jobDTO.setImageURL(jobModel.getImageURL());
            if (categoriesModel != null) {
                jobDTO.setCategory(categoriesModel.getCategoryName());
            }
            jobList.add(jobDTO);
        }
        return jobList;
    }

    public JobDTO getPostedJobDetails(String id) throws InterruptedException, ExecutionException {
        JobDTO jobDTO = new JobDTO();
        DocumentReference docRef = firestore.collection("Job").document(id.trim());
        DocumentSnapshot docSnap = docRef.get().get();
        if (docSnap.exists()) {
            JobModel jobModel = docSnap.toObject(JobModel.class);
            if (jobModel == null)
                return jobDTO;
            jobDTO.setId(jobModel.getId());
            jobDTO.setTitle(jobModel.getTitle());
            jobDTO.setPayment(jobModel.getPayment());
            jobDTO.setImageURL(jobModel.getImageURL());
            jobDTO.setStartDate(jobModel.getStartDate());
            jobDTO.setEndDate(jobModel.getEndDate());
            jobDTO.setJobDescription(jobModel.getJobDescription());

            // Get category
            DocumentSnapshot cateSnap = jobModel.getCategory().get().get();
            JobCategoriesModel categoriesModel = cateSnap.toObject(JobCategoriesModel.class);
            jobDTO.setCategory(categoriesModel.getCategoryName());

            // Get employer
            DocumentReference employerRef = jobModel.getEmployer();
            DocumentReference accountRef = firestore.collection("Account").document(employerRef.getId());
            DocumentSnapshot snap = accountRef.get().get();
            AccountModel accountModel = snap.toObject(AccountModel.class);
            System.out.println(accountModel);
            EmployerDTO empDTO = new EmployerDTO();
            empDTO.setId(employerRef.getId());
            empDTO.setFullName(accountModel.getFullName());
            empDTO.setAvatar(accountModel.getAvatar());
            jobDTO.setEmployer(empDTO);

            // Get status
            DocumentSnapshot statSnap = jobModel.getStatus().get().get();
            JobStatusModel statModel = statSnap.toObject(JobStatusModel.class);
            jobDTO.setStatus(statModel.getStatusName());
        }
        System.out.println(jobDTO);
        return jobDTO;
    }

    public String updateJobStatus(String id, String statusid) throws InterruptedException, ExecutionException {
        DocumentReference statusRef = firestore.collection("JobStatus").document(statusid);
        DocumentReference jobRef = firestore.collection("Job").document(id);
        jobRef.update("status",statusRef);
        return "";
    }

}
