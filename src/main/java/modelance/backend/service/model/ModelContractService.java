package modelance.backend.service.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import modelance.backend.firebasedto.work.ContractDTO;

public class ModelContractService {
    private Firestore firestore;

    public ModelContractService() {
        this.firestore = FirestoreClient.getFirestore();
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

}
