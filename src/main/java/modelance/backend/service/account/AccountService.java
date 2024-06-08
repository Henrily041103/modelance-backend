package modelance.backend.service.account;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import modelance.backend.model.account.AccountModel;
import modelance.backend.model.account.AccountRole;
import modelance.backend.model.account.AccountStatus;
import modelance.backend.model.account.EmployerModel;
import modelance.backend.model.account.ModelModel;
import modelance.backend.config.security.AccountAuthority;;

@Service
public class AccountService {
    private Firestore firestore;

    public AccountService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public List<AccountAuthority> getAuthorities(AccountModel account) {
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

    public AccountModel login(String username, String password)
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
                    account = accountDocument.toObject(AccountModel.class);
                    if (account != null)
                        account.setId(accountDocument.getId());
                } catch (ClassCastException | NullPointerException e) {
                    throw new NoAccountExistsException();
                }
            }
        }
        return account;
    }

    public AccountModel register(String username, String password, String email, String role)
            throws InterruptedException, ExecutionException, NoAccountExistsException {
        AccountModel account = null;

        if (username.trim() != "" && password.trim() != "") {
            ApiFuture<QuerySnapshot> readAccountQuery = firestore.collection("Account")
                    .whereEqualTo("email", email)
                    .get();

            List<QueryDocumentSnapshot> matchedAccount = readAccountQuery.get().getDocuments();

            if (matchedAccount.size() == 0) {
                account = new AccountModel(username, password);
                account.setEmail(email);
                account.setCreateDate(Calendar.getInstance().getTime());
                account.setRole(new AccountRole("1", role));
                account.setStatus(new AccountStatus("1", "active"));
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
                    AccountModel account = documentSnap.toObject(AccountModel.class);
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

    public ModelModel loadModelModel(String id)
            throws InterruptedException, ExecutionException, NoAccountExistsException {
        ModelModel account = null;

        if (id.trim() != "") {
            // DocumentReference modelDoc

        }

        return account;
    }

    public EmployerModel loadEmployerModel(String id)
            throws InterruptedException, ExecutionException, NoAccountExistsException {
        EmployerModel account = null;

        if (id.trim() != "") {
            // DocumentReference modelDoc

        }

        return account;
    }
}
