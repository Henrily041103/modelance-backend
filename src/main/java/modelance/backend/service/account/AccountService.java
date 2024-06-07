package modelance.backend.service.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import modelance.backend.model.account.AccountModel;
import modelance.backend.model.account.AdminModel;
import modelance.backend.model.account.EmployerModel;
import modelance.backend.model.account.ModelModel;
import modelance.backend.config.security.AccountAuthority;
import modelance.backend.dto.AccountDTO;;

@Service
public class AccountService {
    private Firestore firestore;

    public AccountService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public List<AccountAuthority> getAuthorities(AccountDTO accDTO) {
        List<AccountAuthority> authorities = new ArrayList<>();

        switch (accDTO.getRole().getRoleName().toLowerCase()) {
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
        AccountModel account = null;
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
                    @SuppressWarnings("unchecked")
                    Map<String, String> roleMap = (Map<String, String>) accountDocument.get("role");
                    String role = roleMap.get("roleName").toLowerCase();
                    switch (role) {
                        case "admin":
                            account = accountDocument.toObject(AdminModel.class);
                            break;
                        case "employer":
                            account = accountDocument.toObject(EmployerModel.class);
                            break;
                        case "model":
                            account = accountDocument.toObject(ModelModel.class);
                            break;
                        default:
                            throw new NoAccountExistsException();
                    }
                    if (account != null) {
                        account.setId(accountDocument.getReference().getId());
                    }
                } catch (ClassCastException | NullPointerException e) {
                    throw new NoAccountExistsException();
                }
            }
        }
        AccountDTO accDTO = new AccountDTO();
        accDTO.setId(account.getId());
        accDTO.setUsername(account.getUsername());
        accDTO.setPassword(account.getPassword());
        accDTO.setFullName(account.getFullName());
        accDTO.setRole(account.getRole());
        accDTO.setAvatar(account.getAvatar());
        return accDTO;
    }

    public ModelModel loadModelModel(String id)
            throws InterruptedException, ExecutionException, NoAccountExistsException {
        ModelModel account = null;
        if (id.trim() != "") {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            // Model
            DocumentReference docRef = dbFirestore.collection("Model").document(id);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot docSnap = future.get();
            if (docSnap.exists()) {
                account = docSnap.toObject(ModelModel.class);
            }
            // Account
            DocumentReference docAccRef = dbFirestore.collection("Account").document(id);
            ApiFuture<DocumentSnapshot> accfuture = docAccRef.get();
            DocumentSnapshot accDocSnap = accfuture.get();
            if (accDocSnap.exists()) {
                AccountModel acc = accDocSnap.toObject(AccountModel.class);
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
        return account;
    }

    public EmployerModel loadEmployerModel(String id)
            throws InterruptedException, ExecutionException, NoAccountExistsException {
        EmployerModel employerModel = null;
        if (id.trim() != "") {
            // Employer
            Firestore dbFirestore = FirestoreClient.getFirestore();
            DocumentReference docRef = dbFirestore.collection("Employer").document(id);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot docSnap = future.get();
            if (docSnap.exists()) {
                employerModel = docSnap.toObject(EmployerModel.class);
            }
            // Account
            DocumentReference docAccRef = dbFirestore.collection("Account").document(id);
            ApiFuture<DocumentSnapshot> accfuture = docAccRef.get();
            DocumentSnapshot accDocSnap = accfuture.get();
            if (accDocSnap.exists()) {
                AccountModel acc = accDocSnap.toObject(AccountModel.class);
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
        return employerModel;
    }
}
