package modelance.backend.service.account;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;

import modelance.backend.model.account.AccountModel;
import modelance.backend.model.account.AdminModel;
import modelance.backend.model.account.EmployerModel;
import modelance.backend.model.account.ModelModel;

@Service
public class AccountService {
    private Firestore firestore;

    public AccountService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public String generateToken(String id) throws FirebaseAuthException {
        String customToken = FirebaseAuth.getInstance().createCustomToken(id);
        return customToken;
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
                    @SuppressWarnings("unchecked")
                    Map<String, String> roleMap = (Map<String, String>) accountDocument.get("role");
                    @SuppressWarnings("null")
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

        return account;
    }

    public AccountModel loadUserByUsername(String username)
            throws InterruptedException, ExecutionException, NoAccountExistsException {
        AccountModel account = null;

        if (username.trim() != "") {
            ApiFuture<QuerySnapshot> readAccountQuery = firestore.collection("Account")
                    .whereEqualTo("username", username)
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
                    @SuppressWarnings("null")
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
                } catch (ClassCastException | NullPointerException e) {
                    throw new NoAccountExistsException();
                }
            }

        }

        return account;
    }
}
