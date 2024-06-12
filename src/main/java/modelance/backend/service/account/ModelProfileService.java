package modelance.backend.service.account;

import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import modelance.backend.firebasedto.account.AccountDTO;
import modelance.backend.firebasedto.account.ModelDTO;
import modelance.backend.model.ModelProfileModel;

@Service
public class ModelProfileService {
    private Firestore firestore;
    private ObjectMapper objectMapper;

    public ModelProfileService() {
        this.firestore = FirestoreClient.getFirestore();
        this.objectMapper = new ObjectMapper();
    }

    public ModelProfileModel getProfile(String id) throws InterruptedException, ExecutionException {
        ModelProfileModel profileModel = new ModelProfileModel();
        AccountDTO accountDTO = firestore.collection("Account").document(id).get().get()
                .toObject(AccountDTO.class);

        ModelDTO modelDTO = firestore.collection("Model").document(id).get().get().toObject(ModelDTO.class);
        profileModel = objectMapper.convertValue(modelDTO, ModelProfileModel.class);

        profileModel.setId(id);
        profileModel.setUsername(accountDTO.getUsername());
        profileModel.setFullName(accountDTO.getFullName());
        profileModel.setEmail(accountDTO.getEmail());
        profileModel.setAvatar(accountDTO.getAvatar());
        profileModel.setDateOfBirth(accountDTO.getDateOfBirth());
        profileModel.setGender(accountDTO.getGender());
        profileModel.setStatus(accountDTO.getStatus());

        return profileModel;
    }

    public String updateProfile(String id, ModelProfileModel profile) {
        AccountDTO aModel = new AccountDTO();
        ModelDTO mModel = new ModelDTO();
        firestore.collection("Account").document(id).set(aModel);
        firestore.collection("Model").document(id).set(mModel);
        return "";
    }

}
