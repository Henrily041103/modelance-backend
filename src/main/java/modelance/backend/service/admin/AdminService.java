package modelance.backend.service.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import modelance.backend.firebasedto.account.AccountDTO;
import modelance.backend.firebasedto.account.AccountStatusDTO;
import modelance.backend.firebasedto.wallet.BankTransactionDTO;
import modelance.backend.firebasedto.work.ContractDTO;
import modelance.backend.firebasedto.work.ContractDTO.JobDTO;
import modelance.backend.model.AccountDetailsModel;
import modelance.backend.model.AccountModel;
import modelance.backend.model.ContractModel;
import modelance.backend.model.JobItemModel;
import modelance.backend.model.JobModel;
import modelance.backend.model.TransactionModel;

@Service
public class AdminService {
    private Firestore firestore;
    private ObjectMapper objectMapper;

    public AdminService(ObjectMapper objectMapper) {
        this.firestore = FirestoreClient.getFirestore();
        this.objectMapper = objectMapper;
    }

    // USERS MANAGEMENT

    public ArrayList<AccountModel> getAllUsers() throws ExecutionException, InterruptedException {
        ArrayList<AccountModel> accountList = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection("Account").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot snap : documents) {
            AccountDTO accountDTO = snap.toObject(AccountDTO.class);
            AccountModel account = objectMapper.convertValue(accountDTO, AccountModel.class);
            if (account != null) {
                accountList.add(account);
            }
        }
        return accountList;
    }

    public String updateAccountStatus(String doc, String status) throws InterruptedException, ExecutionException {
        AccountStatusDTO statusDTO = new AccountStatusDTO("id", status);
        if (status.equalsIgnoreCase("active")) statusDTO.setId("1");
        else statusDTO.setId("2");
        DocumentReference ref = firestore.collection("Account").document(doc);
        ref.update("status", statusDTO);
        return "Banned account: " + doc;
    }

    public AccountDetailsModel getAccount(String doc) throws InterruptedException, ExecutionException {
        DocumentReference ref = firestore.collection("Account").document(doc);
        AccountDTO dto = ref.get().get().toObject(AccountDTO.class);
        AccountDetailsModel details = objectMapper.convertValue(dto, AccountDetailsModel.class);
        details.setId(doc);
        return details;
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
}