package modelance.backend.service.wallet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import modelance.backend.firebasedto.account.AccountDTO;
import modelance.backend.firebasedto.wallet.TransactionDTO;
import modelance.backend.firebasedto.wallet.WalletDTO;
import modelance.backend.service.account.AccountService;

@Service
public class WalletService {
    private final Firestore firestore;
    private final AccountService accountService;

    public WalletService() {
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
            walletModel.setAccount(account);

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

    public TransactionDTO endOfContractMoneyTransfer(Authentication authentication, String contractId)
            throws InterruptedException, ExecutionException {
        TransactionDTO transaction = null;

        // String employerId = authentication.getName();
        // AccountDTO employer = accountService.getAccountById(employerId);
        
        return transaction;
    }
}
