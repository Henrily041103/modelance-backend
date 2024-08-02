package modelance.backend.service.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import modelance.backend.firebasedto.account.AccountDTO;
import modelance.backend.firebasedto.account.AccountStatusDTO;
import modelance.backend.firebasedto.premium.PremiumPackDTO;
import modelance.backend.firebasedto.premium.PremiumPackRenewalDTO;
import modelance.backend.firebasedto.wallet.BankTransactionDTO;
import modelance.backend.firebasedto.work.ContractDTO;
import modelance.backend.firebasedto.work.ContractDTO.JobDTO;
import modelance.backend.model.ContractModel;
import modelance.backend.model.JobItemModel;
import modelance.backend.model.JobModel;
import modelance.backend.model.TransactionModel;
import modelance.backend.service.account.AccountService;
import modelance.backend.service.account.NoAccountExistsException;

@Service
public class AdminService {
    private Firestore firestore;
    private ObjectMapper objectMapper;
    private AccountService accountService;

    public AdminService(ObjectMapper objectMapper, AccountService accountService) {
        this.objectMapper = objectMapper;
        this.accountService = accountService;
        this.firestore = FirestoreClient.getFirestore();
    }

    public ArrayList<AccountDTO> getAllUsers() throws ExecutionException, InterruptedException {
        ArrayList<AccountDTO> accountList = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection("Account").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot snap : documents) {
            AccountDTO accountDTO = snap.toObject(AccountDTO.class);
            accountDTO.setId(snap.getId());
            if (accountDTO != null) {
                accountList.add(accountDTO);
            }
        }
        return accountList;
    }

    public String updateAccountStatus(String doc, String status) throws InterruptedException, ExecutionException {
        AccountStatusDTO statusDTO = new AccountStatusDTO("id", status);
        if (status.equalsIgnoreCase("active"))
            statusDTO.setId("1");
        else
            statusDTO.setId("2");
        DocumentReference ref = firestore.collection("Account").document(doc);
        ref.update("status", statusDTO);
        return "Banned account: " + doc;
    }

    public AccountDTO getAccountByIdRole(String id, String roleName)
            throws InterruptedException, ExecutionException, NoAccountExistsException {
        return accountService.getAccountByRoleString(id, roleName.toLowerCase());
    }

    // JOBS AND CONTRACTS MANAGEMENT

    public ArrayList<JobItemModel> getAllJobs() throws InterruptedException, ExecutionException {
        ArrayList<JobItemModel> jobsList = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection("Job").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot snap : documents) {
            JobDTO dto = snap.toObject(JobDTO.class);
            JobItemModel jobItem = objectMapper.convertValue(dto, JobItemModel.class);
            if (jobItem != null) {
                jobsList.add(jobItem);
            }
        }
        return jobsList;
    }

    public JobModel getJobDetails(String doc) throws InterruptedException, ExecutionException {
        DocumentReference ref = firestore.collection("Job").document(doc);
        JobDTO dto = ref.get().get().toObject(JobDTO.class);
        JobModel job = objectMapper.convertValue(dto, JobModel.class);
        return job;
    }

    public ContractModel getContractDetails(String doc) throws InterruptedException, ExecutionException {
        DocumentReference ref = firestore.collection("Contract").document(doc);
        ContractDTO dto = ref.get().get().toObject(ContractDTO.class);
        ContractModel contract = objectMapper.convertValue(dto, ContractModel.class);
        return contract;
    }

    // TRANSACTIONS MANAGEMENT

    public ArrayList<TransactionModel> getAllTransaction() throws InterruptedException, ExecutionException {
        ArrayList<TransactionModel> transactionList = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection("Transaction").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot snap : documents) {
            TransactionModel transaction = snap.toObject(TransactionModel.class);
            if (transaction != null) {
                transaction.setId(snap.getId());
                transactionList.add(transaction);
            }
        }
        return transactionList;
    }

    public ArrayList<BankTransactionDTO> getAllBankTransaction() throws InterruptedException, ExecutionException {
        ArrayList<BankTransactionDTO> transactionList = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection("BankTransaction").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot snap : documents) {
            BankTransactionDTO transaction = snap.toObject(BankTransactionDTO.class);
            if (transaction != null) {
                transactionList.add(transaction);
            }
        }
        return transactionList;
    }

    public BankTransactionDTO getBankTransactionByOC(String orderCode) throws InterruptedException, ExecutionException {
        BankTransactionDTO bankTransaction = null;

        QuerySnapshot querySnapshot = firestore.collection("BankTransaction")
                .whereEqualTo("orderCode", orderCode).get().get();
        if (!querySnapshot.isEmpty()) {
            List<QueryDocumentSnapshot> result = querySnapshot.getDocuments();
            bankTransaction = result.get(0).toObject(BankTransactionDTO.class);
        }
        return bankTransaction;
    }

    public TransactionModel getTransactionById(String id) throws InterruptedException, ExecutionException {
        TransactionModel transaction = null;

        DocumentSnapshot docRef = firestore.collection("Transaction").document(id).get().get();
        if (docRef.exists()) {
            transaction = docRef.toObject(TransactionModel.class);
        }

        return transaction;
    }

    // PREMIUM PACK
    public ArrayList<PremiumPackDTO> getPacks() throws InterruptedException, ExecutionException {
        ArrayList<PremiumPackDTO> packs = null;

        QuerySnapshot querySnapshot = firestore.collection("PremiumPack").get().get();
        if (!querySnapshot.isEmpty()) {
            List<QueryDocumentSnapshot> queryResult = querySnapshot.getDocuments();
            packs = new ArrayList<>();
            for (QueryDocumentSnapshot result : queryResult) {
                PremiumPackDTO pack = result.toObject(PremiumPackDTO.class);
                pack.setId(result.getId());
                packs.add(pack);
            }
        }
        return packs;
    }

    public ArrayList<PremiumPackRenewalDTO> getPackRenewals() throws InterruptedException, ExecutionException {
        ArrayList<PremiumPackRenewalDTO> packs = null;

        QuerySnapshot querySnapshot = firestore.collection("PremiumPackRenewal").get().get();
        if (!querySnapshot.isEmpty()) {
            List<QueryDocumentSnapshot> queryResult = querySnapshot.getDocuments();
            packs = new ArrayList<>();
            for (QueryDocumentSnapshot result : queryResult) {
                PremiumPackRenewalDTO pack = result.toObject(PremiumPackRenewalDTO.class);
                pack.setId(result.getId());
                packs.add(pack);
            }
        }
        return packs;
    }
}