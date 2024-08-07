package modelance.backend.service.wallet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.lib.payos.PayOS;
import com.lib.payos.type.ItemData;
import com.lib.payos.type.PaymentData;

import modelance.backend.firebasedto.account.AccountDTO;
import modelance.backend.firebasedto.premium.PremiumPackDTO;
import modelance.backend.firebasedto.premium.PremiumPackRenewalDTO;
import modelance.backend.firebasedto.wallet.BankTransactionDTO;
import modelance.backend.firebasedto.wallet.CheckoutResponseDTO;
import modelance.backend.firebasedto.wallet.OrderTransactionDTO;
import modelance.backend.firebasedto.wallet.PayOSWrapper;
import modelance.backend.firebasedto.wallet.TransactionDTO;
import modelance.backend.firebasedto.wallet.WalletDTO;
import modelance.backend.firebasedto.work.ContractDTO;
import modelance.backend.model.TransactionModel;
import modelance.backend.model.WalletModel;
import modelance.backend.service.account.AccountService;
import modelance.backend.service.account.NoAccountExistsException;

@Service
public class WalletService {
    private final static double EMPLOYER_CONTRACT_MULTIPLIER = 1.05;
    private final static double MODEL_CONTRACT_MULTIPLIER = 0.9;
    // private final static double TOP_UP_MULTIPLIER = 0.9;

    private final Firestore firestore;
    private final AccountService accountService;
    private final ObjectMapper objectMapper;
    private final PayOS payOS;

    public WalletService(AccountService accountService, ObjectMapper objectMapper, PayOS payOS) {
        this.accountService = accountService;
        this.objectMapper = objectMapper;
        this.payOS = payOS;
        this.firestore = FirestoreClient.getFirestore();
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
            WalletModel wallet = walletDoc.toObject(WalletModel.class);
            WalletDTO walletModel = new WalletDTO(walletDoc.getId(), account, wallet.getBalance());

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
            walletModel.setAccount(account);

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
        employerTransaction.set(eTransactionModel);
        modelTransaction.set(mTransactionModel);

        // update wallet
        Map<String, Object> employerWalletUpdate = new HashMap<>();
        Map<String, Object> modelWalletUpdate = new HashMap<>();
        employerWalletUpdate.put("balance",
                employerWalletDTO.getBalance() - contractDTO.getPayment() * EMPLOYER_CONTRACT_MULTIPLIER);
        modelWalletUpdate.put("balance",
                modelWalletDTO.getBalance() + contractDTO.getPayment() * MODEL_CONTRACT_MULTIPLIER);
        DocumentReference employerWallet = firestore.collection("Wallet").document(employerWalletDTO.getId());
        DocumentReference modelWallet = firestore.collection("Wallet").document(modelWalletDTO.getId());
        modelWallet.update(modelWalletUpdate);
        employerWallet.update(employerWalletUpdate);

        // add to results
        transactions.add(modelTransactionDTO);
        transactions.add(employerTransactionDTO);

        return transactions;
    }

    public boolean receiveBankTransaction(JsonNode inputData)
            throws InterruptedException, ExecutionException, JsonProcessingException {

        if (inputData == null) {
            return false;
        }

        // verify and get data
        JsonNode testedData = inputData.get("data");
        // try {
        // testedData = payOS.verifyPaymentWebhookData(inputData);
        // } catch (Exception e) {
        // return false;
        // }
        BankTransactionDTO data = objectMapper.convertValue(testedData, BankTransactionDTO.class);

        // get transaction
        List<QueryDocumentSnapshot> transactionSnapshotList = firestore.collection("Transaction")
                .whereEqualTo("orderCode", data.getOrderCode()).whereEqualTo("status", "pending")
                .get().get().getDocuments();
        if (transactionSnapshotList.size() < 1)
            return false;
        TransactionModel transaction = transactionSnapshotList.get(0).toObject(TransactionModel.class);

        // update transaction
        transaction.setStatus("approved");
        firestore.collection("Transaction")
                .document(transactionSnapshotList.get(0).getId()).set(transaction);
        // transactionDocRef.update("status", "approved");

        // add to bank transaction
        firestore.collection("BankTransaction").add(data);

        // update wallet
        WalletModel wallet = objectMapper.convertValue(transaction.getWallet(), WalletModel.class);
        if (wallet != null && wallet.getId() != null) {
            DocumentReference walletDocRef = firestore.collection("Wallet").document(wallet.getId());
            walletDocRef.update("balance", wallet.getBalance() + data.getAmount());
        }

        return true;
    }

    public CheckoutResponseDTO createBankTransaction(int amount, String description, String baseUrl, Authentication authentication)
            throws InterruptedException, ExecutionException, IOException {
        CheckoutResponseDTO checkoutResponseDTO = null;
        String userId = authentication.getName();

        // create new payment data
        int orderCode = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
        String topUpUrl = baseUrl + "/wallet" + "/topup" + "/";
        String confirmUrl = baseUrl + "/profile";
        String cancelUrl = topUpUrl + "cancelled";

        PaymentData paymentData = new PaymentData(orderCode, amount, description, new ArrayList<ItemData>(), cancelUrl,
                confirmUrl);
        try {
            // send data to payos
            JsonNode jsonNode = payOS.createPaymentLink(paymentData);
            CheckoutResponseDTO paymentLink = objectMapper.convertValue(jsonNode, CheckoutResponseDTO.class);
            if (paymentLink == null)
                return checkoutResponseDTO;

            // get wallet
            WalletDTO wallet = getOwnWallet(authentication);

            // create transaction
            TransactionModel transaction = new TransactionModel();
            transaction.setAmount(amount);
            transaction.setBank(true);
            transaction.setDatetime(Calendar.getInstance().getTime());
            transaction.setOrderCode(orderCode);
            transaction.setStatus("pending");
            transaction.setWallet(wallet.getId(), userId, wallet.getAccount().getRole().getRoleName());
            firestore.collection("Transaction").add(transaction);

            // set result
            checkoutResponseDTO = paymentLink;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return checkoutResponseDTO;
    }

    public OrderTransactionDTO getBankTransaction(String transactionId)
            throws IOException, InterruptedException, ExecutionException {
        OrderTransactionDTO orderTransaction = null;

        // get transaction
        DocumentSnapshot transactionDoc = firestore.collection("Transaction").document(transactionId).get().get();
        if (!transactionDoc.exists())
            return orderTransaction;
        TransactionModel transaction = transactionDoc.toObject(TransactionModel.class);
        if (transaction == null)
            return orderTransaction;
        try {
            // send data to payos
            JsonNode jsonNode = payOS.getPaymentLinkInfomation(transaction.getOrderCode());
            PayOSWrapper<OrderTransactionDTO> paymentData = objectMapper.convertValue(jsonNode,
                    new TypeReference<PayOSWrapper<OrderTransactionDTO>>() {
                    });
            if (paymentData == null)
                return orderTransaction;

            orderTransaction = paymentData.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderTransaction;
    }

    public PremiumPackRenewalDTO purchasePremium(Authentication authentication)
            throws IOException, InterruptedException, ExecutionException, NoPackFoundException,
            NotEnoughMoneyException, NoAccountExistsException {
        PremiumPackRenewalDTO purchase = null;

        // consts
        String userId = authentication.getName();
        String roleName = "";
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            roleName = auth.getAuthority().replace("ROLE_", "").toLowerCase();
            break;
        }
        Date currentDate = Calendar.getInstance().getTime();
        int orderCode = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);

        QuerySnapshot documentSnapshot = firestore.collection("PremiumPack")
                .whereEqualTo("role.roleName", roleName).get().get();

        if (documentSnapshot.isEmpty()) {
            throw new NoPackFoundException();
        }
        List<QueryDocumentSnapshot> snapshots = documentSnapshot.getDocuments();
        if (snapshots.isEmpty()) {
            throw new NoPackFoundException();
        }
        PremiumPackDTO premiumPack = snapshots.get(0).toObject(PremiumPackDTO.class);
        premiumPack.setId(snapshots.get(0).getId());

        // get wallet
        WalletDTO wallet = getOwnWallet(authentication);

        if (wallet.getBalance() < premiumPack.getPackPrice()) {
            throw new NotEnoughMoneyException();
        }

        // create transaction
        TransactionModel transaction = new TransactionModel();
        transaction.setAmount(-premiumPack.getPackPrice());
        transaction.setBank(false);
        transaction.setDatetime(currentDate);
        transaction.setOrderCode(orderCode);
        transaction.setStatus("approved");
        transaction.setWallet(wallet.getId(), userId, wallet.getAccount().getRole().getRoleName());
        DocumentReference transactionRef = firestore.collection("Transaction").document();
        transactionRef.set(transaction);

        // pack purchase
        purchase = new PremiumPackRenewalDTO();
        purchase.setAccount(userId, wallet.getAccount().getRole());
        purchase.setPackId(premiumPack.getId());
        purchase.setRenewalDate(currentDate);
        DocumentReference purchaseRef = firestore.collection("PremiumPackRenewal").document(transactionRef.getId());
        purchaseRef.set(purchase);

        // update wallet
        firestore.collection("Wallet").document(wallet.getId()).update("balance",
                wallet.getBalance() - premiumPack.getPackPrice());

        return purchase;
    }
}
