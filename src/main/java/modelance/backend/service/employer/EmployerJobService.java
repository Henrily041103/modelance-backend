package modelance.backend.service.employer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import modelance.backend.firebasedto.work.ContractDTO;
import modelance.backend.firebasedto.work.JobDTO;
import modelance.backend.model.ContractModel;
import modelance.backend.model.JobModel;
import modelance.backend.model.ModelModel;

@Service
public class EmployerJobService {
    private Firestore firestore;
    private ObjectMapper objectMapper;
    private EmployerContractService contractService;

    private static final List<String> DEFAULT_EMPLOYER_TERMS = new ArrayList<>(Arrays.asList("Lorem ipsum"));
    private static final List<String> DEFAULT_MODEL_TERMS = new ArrayList<>(Arrays.asList("Lorem ipsum"));

    public EmployerJobService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.firestore = FirestoreClient.getFirestore();
    }

    public String createNewJob(JobModel jobModel) throws ExecutionException, InterruptedException {
        JobDTO jobDTO = objectMapper.convertValue(jobModel, JobDTO.class);
        jobDTO.setStatus("4", "Ongoing");

        ApiFuture<DocumentReference> addQuery = firestore.collection("Job").add(jobDTO);
        DocumentReference docRef = addQuery.get();
        return docRef.getId();
    }

    public List<JobModel> getCurrentJobs(Authentication authentication)
            throws InterruptedException, ExecutionException {
        List<JobModel> jobList = new ArrayList<>();
        String userId = authentication.getName();

        // Get all jobs created by employer
        ApiFuture<QuerySnapshot> future = firestore.collection("Job").whereEqualTo("employer.id", userId).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot doc : documents) {
            JobDTO jobDTO = doc.toObject(JobDTO.class);
            JobModel job = objectMapper.convertValue(jobDTO, JobModel.class);
            jobList.add(job);
        }
        return jobList;
    }

    public JobModel updateJobStatus(String id, String statusId)
            throws InterruptedException, ExecutionException, NoJobExistsException {
        DocumentReference jobRef = firestore.collection("Job").document(id);
        JobDTO jobDTO = jobRef.get().get().toObject(JobDTO.class);
        if (jobDTO == null)
            throw new NoJobExistsException();
        JobModel job = objectMapper.convertValue(jobDTO, JobModel.class);
        Map<String, String> jobStatus = new HashMap<>();
        String statusName = "";
        jobStatus.put("id", statusId);
        switch (statusId) {
            case "1":
                statusName = "Open";
                break;
            case "2":
                statusName = "Closed";
                break;
            case "3":
                statusName = "Abandoned";
                break;
            case "4":
                statusName = "Ongoing";
                break;
            case "5":
                statusName = "Finished";
                break;
        }

        job.setStatus(id, statusName);

        jobStatus.put("statusName", statusName);
        jobRef.update("status", jobStatus);

        return job;
    }

    public List<ModelModel> getApplicants(String jobId) throws InterruptedException, ExecutionException {
        List<ModelModel> applicants = null;
        DocumentReference docRef = firestore.collection("Job").document(jobId.trim());
        ApiFuture<DocumentSnapshot> future = docRef.get();
        JobModel jobDTO = future.get().toObject(JobModel.class);
        if (jobDTO != null) {
            applicants = jobDTO.getApplicants();
        }
        return applicants;
    }

    public ContractDTO approveApplicant(String modelId, JobDTO jobDTO)
            throws InterruptedException, ExecutionException {

        JobModel job = objectMapper.convertValue(jobDTO, JobModel.class);
        List<ModelModel> models = job.getApplicants();
        ContractDTO contractDTO = null;

        if (models.size() > 0) {
            ModelModel approvedModel = null;
            for (ModelModel model : models) {
                if (model.getId().equals(modelId)) {
                    approvedModel = model;
                    break;
                }
            }
            if (approvedModel != null) {
                ContractModel contract = new ContractModel();
                contract.setEmployerTerms(DEFAULT_EMPLOYER_TERMS);
                contract.setModelTerms(DEFAULT_MODEL_TERMS);
                contract.setEndDate(job.getEndDate());
                contract.setStartDate(job.getStartDate());
                contract.setModel(approvedModel);
                contract.setJob(job);
                contract.setPayment(job.getPayment());
                contract.setStatus("1", "Pending");

                contractDTO = contractService.addContract(contract);
            }

        }

        return contractDTO;
    }
}
