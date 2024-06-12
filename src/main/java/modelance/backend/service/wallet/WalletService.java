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
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import modelance.backend.firebasedto.account.AccountDTO;
import modelance.backend.firebasedto.work.ContractDTO;
import modelance.backend.firebasedto.work.TransactionDTO;
import modelance.backend.firebasedto.work.WalletDTO;
import modelance.backend.model.TransactionModel;
import modelance.backend.service.account.AccountService;

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

    public WalletDTO getWallet(String userId)
            throws InterruptedException, ExecutionException {
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
            throws InterruptedException, ExecutionException {
        List<TransactionDTO> transactions = new ArrayList<>();

        String userId = authentication.getName();
        AccountDTO account = accountService.getAccountById(userId);

        QuerySnapshot TransactionDTOQuery = firestore.collection("TransactionDTO")
                .whereEqualTo("wallet.account.id", account.getId()).get().get();
        if (TransactionDTOQuery.isEmpty())
            return transactions;
        List<QueryDocumentSnapshot> TransactionDTODocs = TransactionDTOQuery.getDocuments();
        for (QueryDocumentSnapshot TransactionDTODoc : TransactionDTODocs) {
            TransactionDTO TransactionDTO = TransactionDTODoc.toObject(TransactionDTO.class);
            transactions.add(TransactionDTO);
        }

        return transactions;
    }

    public List<TransactionDTO> endOfContractMoneyTransfer(String contractId)
            throws InterruptedException, ExecutionException {
        List<TransactionDTO> transactions = null;

        DocumentSnapshot contractSnap = firestore.collection("Contract").document(contractId).get().get();

        if (contractSnap.exists()) {
            //initialize data
            ContractDTO contractDTO = contractSnap.toObject(ContractDTO.class);
            if (contractDTO == null)
                return transactions;
            transactions = new ArrayList<TransactionDTO>();
            AccountDTO employer = objectMapper.convertValue(contractDTO.getEmployer(), AccountDTO.class);
            AccountDTO model = objectMapper.convertValue(contractDTO.getModel(), AccountDTO.class);
            WalletDTO employerWallet = getWallet(employer.getId());
            WalletDTO modelWallet = getWallet(model.getId());
            Calendar currentTime = Calendar.getInstance();
            Date date = currentTime.getTime();

            if (employerWallet.getBalance() < contractDTO.getPayment() * EMPLOYER_CONTRACT_MULTIPLIER) {
                return transactions;
            }

            //create wallet transactions
            TransactionDTO employerTransaction = new TransactionDTO("", "approved", date, false,
                    (long) (-contractDTO.getPayment() * EMPLOYER_CONTRACT_MULTIPLIER), employerWallet);
            TransactionDTO modelTransaction = new TransactionDTO("", "approved", date, false,
                    (long) (contractDTO.getPayment() * MODEL_CONTRACT_MULTIPLIER), modelWallet);
            TransactionModel eTransactionModel = objectMapper.convertValue(employerTransaction, TransactionModel.class);
            TransactionModel mTransactionModel = objectMapper.convertValue(modelTransaction, TransactionModel.class);
            firestore.collection("Transaction").add(eTransactionModel);
            firestore.collection("Transaction").add(mTransactionModel);

            //update wallet
            Map<String, Object> employerWalletUpdate = new HashMap<>();
            Map<String, Object> modelWalletUpdate = new HashMap<>();
            employerWalletUpdate.put("balance", employerWallet.getBalance() - contractDTO.getPayment() * EMPLOYER_CONTRACT_MULTIPLIER);
            modelWalletUpdate.put("balance", modelWallet.getBalance() + contractDTO.getPayment() * MODEL_CONTRACT_MULTIPLIER);
            firestore.collection("Wallet").document(employerWallet.getId()).update(employerWalletUpdate);
            firestore.collection("Wallet").document(modelWallet.getId()).update(modelWalletUpdate);

            //add to results
            transactions.add(modelTransaction);
            transactions.add(employerTransaction);
        }

        return transactions;
    }
}
