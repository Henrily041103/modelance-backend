package modelance.backend.service.account;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import modelance.backend.config.security.AccountPrincipal;
import modelance.backend.model.account.AccountModel;
import modelance.backend.model.account.AdminModel;
import modelance.backend.model.account.EmployerModel;
import modelance.backend.model.account.ModelModel;

public class AccountDetailService implements UserDetailsService {
    private Firestore firestore;

    public AccountDetailService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountModel account = null;
        try {
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
                    @SuppressWarnings({ "unchecked", "null" })
                    String role = ((Map<String, String>) accountDocument.get("role")).get("roleName");
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
                }

            }
            AccountPrincipal accountPrincipal = new AccountPrincipal(account);
            return accountPrincipal;
        } catch (InterruptedException | ExecutionException | NoAccountExistsException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
