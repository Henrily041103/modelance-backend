package modelance.backend.service.account;

import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import modelance.backend.model.account.AccountModel;

@Service
public class AccountService {
    private Firestore firestore;

    public AccountService() {
        this.firestore = FirestoreClient.getFirestore();
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
                account = accountDocument.toObject(AccountModel.class);
            }

        }

        return account;
    }
}
