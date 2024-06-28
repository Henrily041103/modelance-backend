package modelance.backend.service.wallet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.security.core.Authentication;
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
import com.google.cloud.firestore.WriteBatch;
import com.google.firebase.cloud.FirestoreClient;
import com.lib.payos.PayOS;
import com.lib.payos.type.ItemData;
import com.lib.payos.type.PaymentData;

import modelance.backend.config.payos.PaymentAccount;
import modelance.backend.firebasedto.account.AccountDTO;
import modelance.backend.firebasedto.wallet.BankTransactionDTO;
import modelance.backend.firebasedto.wallet.CheckoutResponseDTO;
import modelance.backend.firebasedto.wallet.OrderTransactionDTO;
import modelance.backend.firebasedto.wallet.PayOSWrapper;
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
    private final static double TOP_UP_MULTIPLIER = 0.9;

    private final Firestore firestore;
    private final AccountService accountService;
    private final ObjectMapper objectMapper;
    private final PayOS payOS;
    private final PaymentAccount paymentAccount;

    public WalletService(AccountService accountService, ObjectMapper objectMapper, PayOS payOS,
            PaymentAccount paymentAccount) {
        this.accountService = accountService;
        this.objectMapper = objectMapper;
        this.payOS = payOS;
        this.paymentAccount = paymentAccount;
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
            WalletDTO walletModel = walletDoc.toObject(WalletDTO.class);
            walletModel.setAccount(account);
            walletModel.setId(walletDoc.getId());

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

        // update everything
        batch.commit();

        // add to results
        transactions.add(modelTransactionDTO);
        transactions.add(employerTransactionDTO);

        return transactions;
    }

    private static Iterator<String> sortedIterator(Iterator<?> it, Comparator<String> comparator) {
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            list.add((String) it.next());
        }

        Collections.sort(list, comparator);
        return list.iterator();
    }

    private String generateSignature(String JSON) {
        String signature = "";

        try {
            if (paymentAccount != null
                    && paymentAccount.getChecksumKey() != null
                    && paymentAccount.getChecksumKey().trim() != "") {
                @SuppressWarnings("unchecked")
                Map<String, Object> jsonObject = new ObjectMapper().readValue(JSON, HashMap.class);
                Iterator<String> sortedIt = sortedIterator(jsonObject.keySet().iterator(), (a, b) -> a.compareTo(b));

                StringBuilder transactionStr = new StringBuilder();
                while (sortedIt.hasNext()) {
                    String key = sortedIt.next();
                    String value = jsonObject.get(key).toString();
                    transactionStr.append(key);
                    transactionStr.append('=');
                    transactionStr.append(value);
                    if (sortedIt.hasNext()) {
                        transactionStr.append('&');
                    }
                }

                signature = new HmacUtils("HmacSHA256", paymentAccount.getChecksumKey())
                        .hmacHex(transactionStr.toString());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return signature;
    }

    public boolean testSignatureWebhook(PayOSWrapper<BankTransactionDTO> data) throws JsonProcessingException {
        // check error
        if (!data.getCode().equals("200") || data.getData().getOrderCode() == 0)
            return false;
        String dataJSON = objectMapper.writeValueAsString(data.getData());
        String signature = generateSignature(dataJSON);

        // check signature
        if (!signature.equals(data.getSignature())) {
            return false;
        }
        return true;
    }

    public boolean receiveBankTransaction(PayOSWrapper<BankTransactionDTO> data)
            throws InterruptedException, ExecutionException, JsonProcessingException {

        if (data != null) {
            // check error
            if (!data.getCode().equals("200") || data.getData().getOrderCode() == 0)
                return false;
            String dataJSON = objectMapper.writeValueAsString(data.getData());
            String signature = generateSignature(dataJSON);

            // check signature
            if (!signature.equals(data.getSignature())) {
                return false;
            }

            // create batch
            WriteBatch batch = firestore.batch();

            // get transaction
            List<QueryDocumentSnapshot> transactionSnapshotList = firestore.collection("Transaction")
                    .whereEqualTo("orderCode", data.getData().getOrderCode()).whereEqualTo("status", "pending")
                    .get().get().getDocuments();
            if (transactionSnapshotList.size() < 1)
                return false;
            TransactionModel transaction = transactionSnapshotList.get(0).toObject(TransactionModel.class);

            // update transaction
            DocumentReference transactionDocRef = firestore.collection("Collection")
                    .document(transactionSnapshotList.get(0).getId());
            batch.update(transactionDocRef, "status", "approved");

            // add to bank transaction
            BankTransactionDTO bankTransaction = data.getData();
            DocumentReference bankTransactionRef = firestore.collection("BankTransaction").document();
            batch.create(bankTransactionRef, bankTransaction);

            // update wallet
            DocumentReference walletDocRef = firestore.collection("Wallet")
                    .document(transaction.getWalletId());
            batch.update(walletDocRef, "amount", data.getData().getAmount() * TOP_UP_MULTIPLIER);

            batch.commit();
        }

        return true;
    }

    public CheckoutResponseDTO createBankTransaction(int amount, Authentication authentication)
            throws InterruptedException, ExecutionException, IOException {
        CheckoutResponseDTO checkoutResponseDTO = null;
        String userId = authentication.getName();

        // create new payment data
        int orderCode = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
        String description = "MODELANCE";
        String baseUrl = "https://modelancefe.vercel.app/";
        String topUpUrl = baseUrl + "/wallet" + "/topup" + "/";
        String confirmUrl = topUpUrl + "success";
        String cancelUrl = topUpUrl + "cancelled";

        PaymentData paymentData = new PaymentData(orderCode, amount, description, new ArrayList<ItemData>(), confirmUrl,
                cancelUrl);
        try {
            // send data to payos
            JsonNode jsonNode = payOS.createPaymentLink(paymentData);
            PayOSWrapper<CheckoutResponseDTO> paymentLink = objectMapper.convertValue(jsonNode,
                    new TypeReference<PayOSWrapper<CheckoutResponseDTO>>() {
                    });
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
            checkoutResponseDTO = paymentLink.getData();
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
}
