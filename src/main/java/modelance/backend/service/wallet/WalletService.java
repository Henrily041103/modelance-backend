package modelance.backend.service.wallet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteBatch;
import com.google.firebase.cloud.FirestoreClient;

import modelance.backend.firebasedto.account.AccountDTO;
// import modelance.backend.firebasedto.wallet.CheckoutResponseDTO;
import modelance.backend.firebasedto.wallet.TransactionDTO;
import modelance.backend.firebasedto.wallet.WalletDTO;
import modelance.backend.firebasedto.work.ContractDTO;
import modelance.backend.model.TransactionModel;
import modelance.backend.service.account.AccountService;
import modelance.backend.service.account.NoAccountExistsException;

@Service
public class WalletService {
    private final static double EMPLOYER_CONTRACT_MULTIPLIER = 1.05;
    private final static double MODEL_CONTRACT_MULTIPLIER = 0.9;

    private final Firestore firestore;
    private final AccountService accountService;
    private final ObjectMapper objectMapper;

    public WalletService(ObjectMapper objectMapper, AccountService accountService) {
        this.objectMapper = objectMapper;
        this.firestore = FirestoreClient.getFirestore();
        this.accountService = accountService;
    }

    public WalletDTO getOwnWallet(Authentication authentication)
            throws InterruptedException, ExecutionException, NoAccountExistsException {
        WalletDTO result = null;

        try {
            AccountDTO account = accountService.getCurrentAccount(authentication);

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

    private WalletDTO getWallet(String userId)
            throws InterruptedException, ExecutionException, NoAccountExistsException {
        WalletDTO result = null;

        try {
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
            throws InterruptedException, ExecutionException, NoAccountExistsException {
        List<TransactionDTO> transactions = new ArrayList<>();

        String userId = authentication.getName();

        QuerySnapshot TransactionDTOQuery = firestore.collection("TransactionDTO")
                .whereEqualTo("wallet.account.id", userId).get().get();
        if (TransactionDTOQuery.isEmpty())
            return transactions;
        List<QueryDocumentSnapshot> TransactionDTODocs = TransactionDTOQuery.getDocuments();
        for (QueryDocumentSnapshot TransactionDTODoc : TransactionDTODocs) {
            TransactionDTO TransactionDTO = TransactionDTODoc.toObject(TransactionDTO.class);
            transactions.add(TransactionDTO);
        }

        return transactions;
    }

    public List<TransactionDTO> endOfContractMoneyTransfer(ContractDTO contractDTO)
            throws InterruptedException, ExecutionException, NoAccountExistsException {
        List<TransactionDTO> transactions = null;

        AccountDTO employer = objectMapper.convertValue(contractDTO.getEmployer(), AccountDTO.class);
        AccountDTO model = objectMapper.convertValue(contractDTO.getModel(), AccountDTO.class);
        WalletDTO employerWalletDTO = getWallet(employer.getId());
        WalletDTO modelWalletDTO = getWallet(model.getId());
        Calendar currentTime = Calendar.getInstance();
        Date date = currentTime.getTime();
        WriteBatch batch = firestore.batch();

        if (employerWalletDTO.getBalance() < contractDTO.getPayment() * EMPLOYER_CONTRACT_MULTIPLIER) {
            return transactions;
        }

        transactions = new ArrayList<TransactionDTO>();
        // create wallet transactions
        TransactionDTO employerTransactionDTO = new TransactionDTO("", "approved", date, false,
                (long) (-contractDTO.getPayment() * EMPLOYER_CONTRACT_MULTIPLIER), employerWalletDTO);
        TransactionDTO modelTransactionDTO = new TransactionDTO("", "approved", date, false,
                (long) (contractDTO.getPayment() * MODEL_CONTRACT_MULTIPLIER), modelWalletDTO);
        TransactionModel eTransactionModel = objectMapper.convertValue(employerTransactionDTO, TransactionModel.class);
        TransactionModel mTransactionModel = objectMapper.convertValue(modelTransactionDTO, TransactionModel.class);
        DocumentReference employerTransaction = firestore.collection("Transaction").document();
        DocumentReference modelTransaction = firestore.collection("Transaction").document();
        batch.set(employerTransaction, eTransactionModel);
        batch.set(modelTransaction, mTransactionModel);

        // update wallet
        Map<String, Object> employerWalletUpdate = new HashMap<>();
        Map<String, Object> modelWalletUpdate = new HashMap<>();
        employerWalletUpdate.put("balance",
                employerWalletDTO.getBalance() - contractDTO.getPayment() * EMPLOYER_CONTRACT_MULTIPLIER);
        modelWalletUpdate.put("balance",
                modelWalletDTO.getBalance() + contractDTO.getPayment() * MODEL_CONTRACT_MULTIPLIER);
        DocumentReference employerWallet = firestore.collection("Wallet").document(employerWalletDTO.getId());
        DocumentReference modelWallet = firestore.collection("Wallet").document(modelWalletDTO.getId());
        batch.update(modelWallet, modelWalletUpdate);
        batch.update(employerWallet, employerWalletUpdate);

        //update everything
        batch.commit();
        
        // add to results
        transactions.add(modelTransactionDTO);
        transactions.add(employerTransactionDTO);

        return transactions;
    }

    // public CheckoutResponseDTO input
}
