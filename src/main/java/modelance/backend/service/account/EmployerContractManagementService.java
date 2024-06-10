package modelance.backend.service.account;

import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import modelance.backend.dto.ContractDTO;
import modelance.backend.dto.JobDTO;
import modelance.backend.dto.ModelDTO;
import modelance.backend.model.account.ModelModel;
import modelance.backend.model.work.ContractModel;
import modelance.backend.model.work.ContractStatusModel;
import modelance.backend.model.work.JobModel;

@Service
public class EmployerContractManagementService {
    private Firestore firestore;

    public EmployerContractManagementService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public String addContract(ContractDTO contractDTO) throws InterruptedException, ExecutionException {
        // Check duplicated jobs
        DocumentReference jobRef = firestore.collection("Job").document(contractDTO.getJob().getId());
        JobModel jobModel = jobRef.get().get().toObject(JobModel.class);
        String jobStatusDoc = jobModel.getStatus().getId();
        if (!jobStatusDoc.equals("1")) return "duplicated job";

        // Contract Model
        ContractModel contractModel = new ContractModel();
        contractModel.setEmployerTerms(contractDTO.getEmployerTerms());
        contractModel.setModelTerms(contractDTO.getModelTerms());
        contractModel.setPayment(contractDTO.getPayment());
        contractModel.setStartTime(contractDTO.getStartTime());
        contractModel.setEndTime(contractDTO.getEndTime());

        // Get Model reference
        DocumentReference modelRef = firestore.collection("Model").document(contractDTO.getModel().getId());
        contractModel.setModel(modelRef);

        // Get Job reference
        contractModel.setJob(jobRef);

        // Get Contract status reference
        DocumentReference statRef = firestore.collection("ContractStatus").document(contractDTO.getJob().getId());
        contractModel.setStatus(statRef);

        //Update job status
        DocumentReference jobStatusRef = firestore.collection("JobStatus").document("2");
        jobRef.update("status", jobStatusRef);
        System.out.println(jobStatusRef + "2");

        firestore.collection("Contract").add(contractModel);
        return "Added contract";
    }

    public ContractDTO viewContract(String id) throws InterruptedException, ExecutionException {
        // Get contract model
        DocumentReference contractRef = firestore.collection("Contract").document(id);
        ContractModel contractModel = contractRef.get().get().toObject(ContractModel.class);

        // Generate contract DTO to send to controller
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setEmployerTerms(contractModel.getEmployerTerms());
        contractDTO.setModelTerms(contractModel.getModelTerms());
        contractDTO.setPayment(contractModel.getPayment());
        contractDTO.setStartTime(contractModel.getStartTime());
        contractDTO.setEndTime(contractModel.getEndTime());

        // Get Model
        DocumentReference modelRef = contractModel.getModel();
        ModelModel model = modelRef.get().get().toObject(ModelModel.class);
        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setId(modelRef.getId());
        modelDTO.setFullName(model.getFullName());
        modelDTO.setAvatar(model.getAvatar());
        contractDTO.setModel(modelDTO);

        // Get Job
        DocumentReference jobRef = contractModel.getJob();
        JobModel jobModel = jobRef.get().get().toObject(JobModel.class);
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(jobRef.getId());
        jobDTO.setTitle(jobModel.getTitle());
        contractDTO.setJob(jobDTO);

        // Get contract status
        DocumentReference statusRef = contractModel.getStatus();
        ContractStatusModel statusModel = statusRef.get().get().toObject(ContractStatusModel.class);
        contractDTO.setStatus(statusModel.getStatusName());

        return contractDTO;
    }
}
