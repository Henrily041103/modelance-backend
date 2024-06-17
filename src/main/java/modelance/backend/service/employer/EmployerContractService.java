package modelance.backend.service.employer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import modelance.backend.firebasedto.work.ContractDTO;
import modelance.backend.firebasedto.work.JobDTO;
import modelance.backend.model.ContractModel;

@Service
public class EmployerContractService {
    private Firestore firestore;
    private ObjectMapper objectMapper;

    public EmployerContractService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.firestore = FirestoreClient.getFirestore();
    }

    ContractDTO addContract(ContractModel contractModel) throws InterruptedException, ExecutionException {
        // Check duplicated jobs
        DocumentReference jobRef = firestore.collection("Job").document(contractModel.getJob().getId());
        ContractDTO contractDTO = null;
        JobDTO jobDTO = jobRef.get().get().toObject(JobDTO.class);
        if (jobDTO == null)
            return contractDTO;

        // Contract Model
        contractDTO = objectMapper.convertValue(contractModel, ContractDTO.class);

        // Update job status
        DocumentReference jobStatusRef = firestore.collection("JobStatus").document("2");
        jobRef.update("status", jobStatusRef);

        firestore.collection("Contract").add(contractDTO);
        return contractDTO;
    }

    public ContractModel viewContract(String id) throws InterruptedException, ExecutionException {
        // Get contract DTO
        DocumentReference contractRef = firestore.collection("Contract").document(id);
        ContractDTO contractDTO = contractRef.get().get().toObject(ContractDTO.class);

        // Generate contract model to send to controller
        ContractModel contractModel = objectMapper.convertValue(contractDTO, ContractModel.class);

        return contractModel;
    }

    public String changeContract(String id, List<String> employerTerms, long payment, Date startDate, Date endDate)
            throws InterruptedException, ExecutionException {
        Map<String, Object> changeMap = new HashMap<>();
        changeMap.put("employerTerms", employerTerms);
        changeMap.put("payment", payment);
        changeMap.put("startDate", startDate);
        changeMap.put("endDate", endDate);

        firestore.collection("Contract").document(id).update(changeMap);
        return "Success";
    }
}
