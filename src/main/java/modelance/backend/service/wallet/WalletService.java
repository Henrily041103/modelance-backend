package modelance.backend.service.wallet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import modelance.backend.firebasedto.account.AccountDTO;
// import modelance.backend.firebasedto.account.AccountRoleDTO;
import modelance.backend.firebasedto.wallet.TransactionDTO;
import modelance.backend.firebasedto.wallet.WalletDTO;
import modelance.backend.firebasedto.work.ContractDTO;
// import modelance.backend.model.AccountModel;
// import modelance.backend.model.ContractModel;
import modelance.backend.service.account.AccountService;

@Service
public class WalletService {
    private final Firestore firestore;
    private final AccountService accountService;
    // private final ObjectMapper objectMapper;

    public WalletService(ObjectMapper objectMapper) {
        // this.objectMapper = objectMapper;
        this.firestore = FirestoreClient.getFirestore();
        this.accountService = new AccountService();
    }

    public WalletDTO getWallet(Authentication authentication)
            throws InterruptedException, ExecutionException {
        WalletDTO result = null;

        try {
            String userId = authentication.getName();
            AccountDTO account = accountService.getAccountById(userId);

            QuerySnapshot walletQuery = firestore.collection("Wallet").whereEqualTo("account.id", account.getId()).get()
                    .get();
            if (walletQuery.isEmpty())
                throw new NoWalletExistsException();
            QueryDocumentSnapshot walletDoc = walletQuery.getDocuments().get(0);
            if (!walletDoc.exists())
                throw new NoWalletExistsException();
            WalletDTO walletModel = walletDoc.toObject(WalletDTO.class);
            // walletModel.setAccount(account);

            result = walletModel;

        } catch (NoWalletExistsException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<TransactionDTO> getTransactions(Authentication authentication)
            throws InterruptedException, ExecutionException {
        List<TransactionDTO> transactions = new ArrayList<>();

        String userId = authentication.getName();
        AccountDTO account = accountService.getAccountById(userId);

        QuerySnapshot transactionQuery = firestore.collection("Transaction")
                .whereEqualTo("wallet.account.id", account.getId()).get().get();
        if (transactionQuery.isEmpty())
            return transactions;
        List<QueryDocumentSnapshot> transactionDocs = transactionQuery.getDocuments();
        for (QueryDocumentSnapshot transactionDoc : transactionDocs) {
            TransactionDTO transaction = transactionDoc.toObject(TransactionDTO.class);
            transactions.add(transaction);
        }

        return transactions;
    }

    public TransactionDTO endOfContractMoneyTransfer(String contractId)
            throws InterruptedException, ExecutionException {
        TransactionDTO transaction = null;

        // String employerId = authentication.getName();
        // AccountDTO employer = new AccountDTO(employerId);
        // employer.setRole(new AccountRoleDTO("3","employer"));

        DocumentSnapshot contractSnap = firestore.collection("Contract").document(contractId).get().get();

        if (contractSnap.exists()) {
            ContractDTO contractDTO = contractSnap.toObject(ContractDTO.class);
            if (contractDTO == null) return transaction;
            // AccountDTO employer = objectMapper.convertValue(contractDTO.getEmployer(), AccountDTO.class);
            // AccountDTO model = objectMapper.convertValue(contractDTO.getModel(), AccountDTO.class);


        }

        return transaction;
    }
}
