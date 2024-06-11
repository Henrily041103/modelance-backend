package modelance.backend.service.account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;

import modelance.backend.config.security.AccountAuthority;
import modelance.backend.firebasedto.account.AccountDTO;
import modelance.backend.firebasedto.account.AccountRoleDTO;
import modelance.backend.firebasedto.account.AccountStatusDTO;
import modelance.backend.firebasedto.account.EmployerDTO;
import modelance.backend.firebasedto.account.ModelDTO;

@Service
public class AccountService {
    private Firestore firestore;
    private StorageClient storageClient;

    public AccountService() {
        this.firestore = FirestoreClient.getFirestore();
        this.storageClient = StorageClient.getInstance();
    }

    public List<AccountAuthority> getAuthorities(AccountDTO account) {
        List<AccountAuthority> authorities = new ArrayList<>();

        switch (account.getRole().getRoleName().toLowerCase()) {
            case "admin":
                authorities.add(new AccountAuthority("ADMIN"));
                break;
            case "model":
                authorities.add(new AccountAuthority("MODEL"));
                break;
            case "employer":
                authorities.add(new AccountAuthority("EMPLOYER"));
                break;
        }
        return authorities;
    }

    public AccountDTO login(String username, String password)
            throws InterruptedException, ExecutionException, NoAccountExistsException {
        AccountDTO accountDTO = null;
        if (username.trim() != "" && password.trim() != "") {
            ApiFuture<QuerySnapshot> readAccountQuery = firestore.collection("Account")
                    .whereEqualTo("username", username).whereEqualTo("password", password)
                    .get();
            List<QueryDocumentSnapshot> matchedAccount = readAccountQuery.get().getDocuments();
            if (matchedAccount.size() == 0) {
                throw new NoAccountExistsException();
            }
            DocumentSnapshot accountDocument = matchedAccount.get(0);
            if (accountDocument.exists()) {
                try {
                    accountDTO = accountDocument.toObject(AccountDTO.class);
                    if (accountDTO != null)
                        accountDTO.setId(accountDocument.getId());
                } catch (ClassCastException | NullPointerException e) {
                    throw new NoAccountExistsException();
                }
            }
        }
        return accountDTO;
    }

    public AccountDTO register(String username, String password, String email, String role)
            throws InterruptedException, ExecutionException, NoAccountExistsException {
        AccountDTO account = null;

        if (username.trim() != "" && password.trim() != "") {
            ApiFuture<QuerySnapshot> readAccountQuery = firestore.collection("Account")
                    .whereEqualTo("email", email)
                    .get();

            List<QueryDocumentSnapshot> matchedAccount = readAccountQuery.get().getDocuments();

            if (matchedAccount.size() == 0) {
                account = new AccountDTO(username, password);
                account.setEmail(email);
                account.setCreateDate(Calendar.getInstance().getTime());
                account.setRole(new AccountRoleDTO("1", role));
                account.setStatus(new AccountStatusDTO("1", "active"));
                ApiFuture<DocumentReference> addQuery = firestore.collection("Account").add(account);
                DocumentReference addDoc = addQuery.get();
                account.setId(addDoc.getId());
            }

        }
        return account;
    }

    public boolean changePassword(String oldPassword, String newPassword, Authentication authentication)
            throws InterruptedException, ExecutionException, NoAccountExistsException {
        String userId = authentication.getName();
        boolean result = false;

        if (oldPassword.trim() != "" && newPassword.trim() != "") {
            DocumentSnapshot documentSnap = firestore.collection("Account").document(userId).get().get();
            if (documentSnap.exists()) {
                try {
                    AccountDTO account = documentSnap.toObject(AccountDTO.class);
                    if (account != null && account.getPassword().equals(oldPassword)) {
                        firestore.collection("Account").document(userId).update("password", newPassword);
                        result = true;
                    }
                } catch (ClassCastException | NullPointerException e) {
                    throw new NoAccountExistsException();
                }
            }
        }

        return result;
    }

    public boolean changeAccountData(Map<String, Object> updates, Authentication authentication)
            throws InterruptedException, ExecutionException, NoAccountExistsException {
        String userId = authentication.getName();
        boolean result = false;

        DocumentSnapshot documentSnap = firestore.collection("Account").document(userId).get().get();
        if (documentSnap.exists()) {
            try {
                Map<String, Object> checkedUpdates = new HashMap<>();
                for (Map.Entry<String, Object> entry : updates.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (value != null) {
                        checkedUpdates.put(key, value);
                    }
                }

                firestore.collection("Account").document(userId).update(checkedUpdates);
                result = true;

            } catch (ClassCastException | NullPointerException e) {
                throw new NoAccountExistsException();
            }
        }

        return result;
    }

    public ModelDTO loadModelModel(String id)
            throws InterruptedException, ExecutionException, NoAccountExistsException {
        ModelDTO account = null;
        if (id.trim() != "") {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            // Model
            DocumentReference docRef = dbFirestore.collection("Model").document(id);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot docSnap = future.get();
            if (docSnap.exists()) {
                account = docSnap.toObject(ModelDTO.class);
            }
            // Account
            DocumentReference docAccRef = dbFirestore.collection("Account").document(id);
            ApiFuture<DocumentSnapshot> accfuture = docAccRef.get();
            DocumentSnapshot accDocSnap = accfuture.get();
            if (accDocSnap.exists()) {
                AccountDTO acc = accDocSnap.toObject(AccountDTO.class);
                if (account != null && acc != null) {
                    account.setId(id);
                    account.setAvatar(acc.getAvatar());
                    account.setCreateDate(acc.getCreateDate());
                    account.setDateOfBirth(acc.getDateOfBirth());
                    account.setEmail(acc.getEmail());
                    account.setFullName(acc.getFullName());
                    account.setGender(acc.getGender());
                    account.setRole(acc.getRole());
                    account.setStatus(acc.getStatus());
                    account.setUsername(acc.getUsername());
                }
            }
        }
        return account;
    }

    public EmployerDTO loadEmployerModel(String id)
            throws InterruptedException, ExecutionException, NoAccountExistsException {
        EmployerDTO employerModel = null;
        if (id.trim() != "") {
            // Employer
            Firestore dbFirestore = FirestoreClient.getFirestore();
            DocumentReference docRef = dbFirestore.collection("Employer").document(id);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot docSnap = future.get();
            if (docSnap.exists()) {
                employerModel = docSnap.toObject(EmployerDTO.class);
            }
            // Account
            DocumentReference docAccRef = dbFirestore.collection("Account").document(id);
            ApiFuture<DocumentSnapshot> accfuture = docAccRef.get();
            DocumentSnapshot accDocSnap = accfuture.get();
            if (accDocSnap.exists()) {
                AccountDTO acc = accDocSnap.toObject(AccountDTO.class);
                if (acc != null && employerModel != null) {
                    employerModel.setId(id);
                    employerModel.setAvatar(acc.getAvatar());
                    employerModel.setCreateDate(acc.getCreateDate());
                    employerModel.setDateOfBirth(acc.getDateOfBirth());
                    employerModel.setEmail(acc.getEmail());
                    employerModel.setFullName(acc.getFullName());
                    employerModel.setGender(acc.getGender());
                    employerModel.setRole(acc.getRole());
                    employerModel.setStatus(acc.getStatus());
                    employerModel.setUsername(acc.getUsername());
                }
            }
        }
        return employerModel;
    }

    public String uploadAvatar(MultipartFile file, Authentication authentication)
            throws IOException, NoAccountExistsException, InterruptedException, ExecutionException {
        String userId = authentication.getName();
        String filename = file.getOriginalFilename();
        String filetype = filename.substring(filename.lastIndexOf(".") + 1);
        String filepath = "avatar/" + userId + "." + filetype;

        Bucket bucket = storageClient.bucket();

        bucket.create(filepath, file.getInputStream());
        
        String url = "https://firebasestorage.googleapis.com/v0/b/modelance-84abf.appspot.com/o/avatar%2F"
                + userId + "."
                + filetype
                + "?alt=media";

        DocumentSnapshot documentSnap = firestore.collection("Account").document(userId).get().get();
        if (documentSnap.exists()) {
            try {
                firestore.collection("Account").document(userId).update("avatar", url);

            } catch (ClassCastException | NullPointerException e) {
                throw new NoAccountExistsException();
            }
        }

        return url;
    }

    public AccountDTO getAccountById(String userId) throws InterruptedException, ExecutionException {
        AccountDTO account = null;

        try {
            DocumentSnapshot employerDoc = firestore.collection("Account").document(userId).get().get();
            if (!employerDoc.exists())
                throw new NoAccountExistsException();

            AccountDTO employer = employerDoc.toObject(AccountDTO.class);
            if (employer == null)
                throw new NoAccountExistsException();
            employer.setId(userId);
        } catch (NoAccountExistsException e) {
            e.printStackTrace();
        }

        return account;
    }
}
