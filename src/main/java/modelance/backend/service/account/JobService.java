package modelance.backend.service.account;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import modelance.backend.dto.JobDTO;
import modelance.backend.model.work.JobCategoriesModel;
import modelance.backend.model.work.JobModel;

@Service
public class JobService {
    private Firestore firestore;

    public JobService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public ArrayList<JobDTO> getJobListService() throws ExecutionException, InterruptedException {
        ArrayList<JobDTO> jobList = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection("Job").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot doc : documents) {
            JobModel jobModel = doc.toObject(JobModel.class);
            
            //Get Job category
            DocumentSnapshot docSnap = jobModel.getCategory().get().get();
            JobCategoriesModel categoriesModel = docSnap.toObject(JobCategoriesModel.class);

            //JobDTO
            JobDTO jobDTO = new JobDTO();
            jobDTO.setId(jobModel.getId());
            jobDTO.setTitle(jobModel.getJobDescription());
            jobDTO.setImageURL(jobModel.getImageURL());
            jobDTO.setCategory(categoriesModel.getCategoryName());
            jobDTO.setPayment(jobModel.getPayment());
            jobList.add(jobDTO);
        }

        return jobList;
    }
}
