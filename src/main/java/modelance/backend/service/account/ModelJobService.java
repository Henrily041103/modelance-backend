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
import modelance.backend.firebasedto.account.EmployerDTO;
import modelance.backend.firebasedto.work.JobCategoriesDTO;
import modelance.backend.firebasedto.work.JobDTO;
import modelance.backend.firebasedto.work.JobStatusDTO;
import modelance.backend.model.EmployerModel;
import modelance.backend.model.JobModel;

@Service
public class ModelJobService {
    private Firestore firestore;

    public ModelJobService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    @SuppressWarnings("null")
    public ArrayList<JobModel> getJobListService() throws ExecutionException, InterruptedException {
        ArrayList<JobModel> jobList = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection("Job").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot doc : documents) {
            JobDTO jobDTO = doc.toObject(JobDTO.class);
            // Get Job category
            DocumentSnapshot docSnap = jobDTO.getCategory().get().get();
            JobCategoriesDTO categoriesModel = docSnap.toObject(JobCategoriesDTO.class);
            // JobDTO
            JobModel jobModel = new JobModel();
            jobModel.setId(jobDTO.getId());
            jobModel.setTitle(jobDTO.getTitle());
            jobModel.setImageURL(jobDTO.getImageURL());
            jobModel.setCategory(categoriesModel.getCategoryName());
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
            DocumentSnapshot cateSnap = jobDTO.getCategory().get().get();
            JobCategoriesDTO categoriesDTO = cateSnap.toObject(JobCategoriesDTO.class);
            jobModel.setCategory(categoriesDTO.getCategoryName());

            // Get employer
            DocumentSnapshot empSnap = jobDTO.getEmployer().get().get();
            EmployerDTO empDTO = empSnap.toObject(EmployerDTO.class);
            EmployerModel empModel = new EmployerModel();
            empModel.setId(empDTO.getId());
            empModel.setFullName(empDTO.getFullName());
            empModel.setAvatar(empDTO.getAvatar());
            jobModel.setEmployer(empModel);

            // Get status
            DocumentSnapshot statSnap = jobDTO.getStatus().get().get();
            JobStatusDTO statDTO = statSnap.toObject(JobStatusDTO.class);
            jobModel.setStatus(statDTO.getStatusName());
        }
        return jobModel;
    }
}