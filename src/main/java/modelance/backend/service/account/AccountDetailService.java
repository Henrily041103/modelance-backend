package modelance.backend.service.account;

import java.util.concurrent.ExecutionException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import modelance.backend.config.security.AccountPrincipal;
import modelance.backend.model.account.AccountModel;

public class AccountDetailService implements UserDetailsService {
    private Firestore firestore;

    public AccountDetailService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        AccountModel account = null;
        try {
            if (userId.trim() != "") {
                ApiFuture<DocumentSnapshot> query = firestore.collection("Account").document(userId).get();
                DocumentSnapshot document = query.get();
                if (document.exists()) {
                    account = document.toObject(AccountModel.class);
                }
            }
            AccountPrincipal accountPrincipal = new AccountPrincipal(account);
            return accountPrincipal;
        } catch (InterruptedException | ExecutionException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
