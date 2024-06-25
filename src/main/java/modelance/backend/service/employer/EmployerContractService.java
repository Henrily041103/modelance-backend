package modelance.backend.service.employer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import modelance.backend.firebasedto.wallet.TransactionDTO;
import modelance.backend.firebasedto.work.ContractDTO;
import modelance.backend.service.account.NoAccountExistsException;
import modelance.backend.service.job.ContractService;
import modelance.backend.service.wallet.WalletService;

@Service
public class EmployerContractService {
    private Firestore firestore;
    private ContractService contractService;
    private WalletService walletService;
    private EmployerJobService jobService;

    public EmployerContractService(ContractService contractService, WalletService walletService, EmployerJobService jobService) {
        this.firestore = FirestoreClient.getFirestore();
        this.contractService = contractService;
        this.walletService = walletService;
        this.jobService = jobService;
    }

    public List<ContractDTO> getCurrentContracts(Authentication authentication)
            throws InterruptedException, ExecutionException {
        String userId = authentication.getName();
        List<ContractDTO> contracts = null;

        QuerySnapshot contractQuery = firestore.collection("Contract").whereEqualTo("employer.id", userId).get().get();
        if (!contractQuery.isEmpty()) {
            List<QueryDocumentSnapshot> contractDocuments = contractQuery.getDocuments();
            if (contractDocuments != null && !contractDocuments.isEmpty()) {
                contracts = new ArrayList<>();
                for (QueryDocumentSnapshot contractDoc : contractDocuments) {
                    ContractDTO contract = contractDoc.toObject(ContractDTO.class);
                    contracts.add(contract);
                }
            }
        }

        return contracts;
    }

    public Map<String, Object> finishContract(String contractId) throws ExecutionException, InterruptedException, NoJobExistsException, NoAccountExistsException {
        Map<String, Object> result = null;

        ContractDTO contract = contractService.getContract(contractId);

        result = new HashMap<>();
        result.put("message", "Failed");

        if (contract != null) {
            contract.setStatus("4");
            contractService.changeContractStatus("Finished", contractId);
            jobService.updateJobStatus(contract.getJob().getId(), "5");
            List<TransactionDTO> transactions = walletService.endOfContractMoneyTransfer(contract);
            result.put("message", "Success");
            result.put("transactions", transactions);
        }

        return result;
    }
}
