package modelance.backend.service.wallet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import modelance.backend.dto.ContractDTO;
import modelance.backend.model.account.AccountModel;
import modelance.backend.model.wallet.TransactionModel;
import modelance.backend.model.wallet.WalletModel;
import modelance.backend.service.account.AccountService;

@Service
public class WalletService {
    private final Firestore firestore;
    private final AccountService accountService;

    public WalletService(Firestore firestore, AccountService accountService) {
        this.firestore = firestore;
        this.accountService = accountService;
    }

    public WalletModel getWallet(Authentication authentication)
            throws InterruptedException, ExecutionException {
        WalletModel result = null;

        try {
            String userId = authentication.getName();
            AccountModel account = accountService.getAccountById(userId);

            QuerySnapshot walletQuery = firestore.collection("Wallet").whereEqualTo("account.id", account.getId()).get()
                    .get();
            if (walletQuery.isEmpty())
                throw new NoWalletExistsException();
            QueryDocumentSnapshot walletDoc = walletQuery.getDocuments().get(0);
            if (!walletDoc.exists())
                throw new NoWalletExistsException();
            WalletModel walletModel = walletDoc.toObject(WalletModel.class);
            walletModel.setAccount(account);

            result = walletModel;

        } catch (NoWalletExistsException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<TransactionModel> getTransactions(Authentication authentication)
            throws InterruptedException, ExecutionException {
        List<TransactionModel> transactions = new ArrayList<>();

        String userId = authentication.getName();
        AccountModel account = accountService.getAccountById(userId);

        QuerySnapshot transactionQuery = firestore.collection("Transaction")
                .whereEqualTo("wallet.account.id", account.getId()).get().get();
        if (transactionQuery.isEmpty())
            return transactions;
        List<QueryDocumentSnapshot> transactionDocs = transactionQuery.getDocuments();
        for (QueryDocumentSnapshot transactionDoc : transactionDocs) {
            TransactionModel transaction = transactionDoc.toObject(TransactionModel.class);
            transactions.add(transaction);
        }

        return transactions;
    }

    public TransactionModel endOfContractMoneyTransfer(Authentication authentication, String contractId)
            throws InterruptedException, ExecutionException {
        TransactionModel transaction = null;

        String employerId = authentication.getName();
        AccountModel employer = accountService.getAccountById(employerId);
        // ContractDTO contract = 


        return transaction;
    }
}
