package modelance.backend.service.account;

import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import modelance.backend.firebasedto.work.ContractDTO;
import modelance.backend.firebasedto.work.JobDTO;
import modelance.backend.model.ContractModel;
import modelance.backend.model.JobStatusModel;

@Service
public class EmployerContractManagementService {
    private Firestore firestore;
    private ObjectMapper objectMapper;

    public EmployerContractManagementService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.firestore = FirestoreClient.getFirestore();
        this.objectMapper = new ObjectMapper();
    }

    public String addContract(ContractModel contractModel) throws InterruptedException, ExecutionException {
        // Check duplicated jobs
        DocumentReference jobRef = firestore.collection("Job").document(contractModel.getJob().getId());
        JobDTO jobDTO = jobRef.get().get().toObject(JobDTO.class);
        String jobStatus = jobDTO.getStatus().getId();
        if (!jobStatus.equals("1"))
            return "duplicated job";
        // Contract Model -> DTO
        ContractDTO contractDTO = objectMapper.convertValue(contractModel, ContractDTO.class);
        // Update job status
        jobRef.update("status", new JobStatusModel("2", "Closed"));
        firestore.collection("Contract").add(contractDTO);
        return "Added contract";
    }

    public ContractModel viewContract(String id) throws InterruptedException, ExecutionException {
        // Get contract DTO
        DocumentReference contractRef = firestore.collection("Contract").document(id);
        ContractDTO contractDTO = contractRef.get().get().toObject(ContractDTO.class);
        // Contract DTO -> Model
        ContractModel contractModel = objectMapper.convertValue(contractDTO, ContractModel.class);
        return contractModel;
    }
}
