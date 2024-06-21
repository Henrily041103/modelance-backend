package modelance.backend.service.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;

import modelance.backend.firebasedto.work.ContractDTO;
import modelance.backend.firebasedto.work.JobDTO;
import modelance.backend.model.ContractModel;

public class ContractService {

    private Firestore firestore;
    private ObjectMapper objectMapper;

    public ContractService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.firestore = FirestoreClient.getFirestore();
    }

    public ContractDTO addContract(ContractModel contractModel) throws InterruptedException, ExecutionException {
        // Check duplicated jobs
        DocumentReference jobRef = firestore.collection("Job").document(contractModel.getJob().getId());
        ContractDTO contractDTO = null;
        JobDTO jobDTO = jobRef.get().get().toObject(JobDTO.class);
        if (jobDTO == null)
            return contractDTO;

        // Contract Model
        contractDTO = objectMapper.convertValue(contractModel, ContractDTO.class);
        contractDTO.setStatus("1");

        firestore.collection("Contract").add(contractDTO);
        return contractDTO;
    }

    public ContractDTO getContract(String id) throws InterruptedException, ExecutionException {
        // Get contract DTO
        DocumentReference contractRef = firestore.collection("Contract").document(id);
        ContractDTO contractDTO = contractRef.get().get().toObject(ContractDTO.class);

        return contractDTO;
    }

    public ContractDTO changeContract(String id, List<String> employerTerms, long payment, Date startDate, Date endDate)
            throws InterruptedException, ExecutionException {
        DocumentReference contractRef = firestore.collection("Contract").document(id);
        ContractDTO contractDTO = contractRef.get().get().toObject(ContractDTO.class);

        if (contractDTO != null) {
            contractDTO.setEmployerTerms(employerTerms);
            contractDTO.setPayment(payment);
            contractDTO.setStartDate(startDate);
            contractDTO.setEndDate(endDate);

            Map<String, Object> changeMap = new HashMap<>();
            changeMap.put("employerTerms", employerTerms);
            changeMap.put("payment", payment);
            changeMap.put("startDate", startDate);
            changeMap.put("endDate", endDate);

            contractRef.update(changeMap);
        }

        return contractDTO;
    }

    public ContractDTO changeContractStatus(String statusName, String contractId)
            throws InterruptedException, ExecutionException {
        DocumentReference contractRef = firestore.collection("Contract").document(contractId);
        ContractDTO contractDTO = contractRef.get().get().toObject(ContractDTO.class);

        if (contractDTO != null) {
            contractDTO.setStatusName(statusName);

            contractRef.update("status", contractDTO.getStatus());
        }

        return contractDTO;
    }

    

    public List<ContractDTO> viewContractsByJob(String jobId) throws InterruptedException, ExecutionException {
        // Get contract DTO
        Query contractRef = firestore.collection("Contract").whereEqualTo("job.id", jobId);
        List<QueryDocumentSnapshot> contractDocs = contractRef.get().get().getDocuments();
        List<ContractDTO> contractDTOs = new ArrayList<>();
        for (QueryDocumentSnapshot contractDoc: contractDocs) {
            ContractDTO contract = contractDoc.toObject(ContractDTO.class);
            if (contract != null) {
                contractDTOs.add(contract);
            }
        }

        return contractDTOs;
    }
}
