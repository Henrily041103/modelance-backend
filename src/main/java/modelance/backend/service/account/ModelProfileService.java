package modelance.backend.service.account;

import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import modelance.backend.firebasedto.account.AccountDTO;
import modelance.backend.firebasedto.account.ModelDTO;
import modelance.backend.model.LocationModel;
import modelance.backend.model.ModelBodyIndexModel;
import modelance.backend.model.ModelProfileModel;

@Service
public class ModelProfileService {
    private Firestore firestore;

    public ModelProfileService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public ModelProfileModel getProfile(String id) throws InterruptedException, ExecutionException {
        AccountDTO accountModel = firestore.collection("Account").document(id).get().get()
                .toObject(AccountDTO.class);
        ModelDTO modelModel = firestore.collection("Model").document(id).get().get().toObject(ModelDTO.class);

        ModelProfileModel profileDTO = new ModelProfileModel();
        profileDTO.setId(id);
        profileDTO.setUsername(accountModel.getUsername());
        profileDTO.setFullName(accountModel.getFullName());
        profileDTO.setEmail(accountModel.getEmail());
        profileDTO.setAvatar(accountModel.getAvatar());
        profileDTO.setDateOfBirth(accountModel.getDateOfBirth());
        profileDTO.setGender(accountModel.getGender().getGenderName());
        profileDTO.setAccountStatus(accountModel.getStatus().getStatusName());
        profileDTO.setIndustry(modelModel.getIndustry().getIndustryName());

        LocationModel location = new LocationModel();
        location.setAndress(modelModel.getLocation().getAddress());
        location.setDistrict(modelModel.getLocation().getDistrict());
        location.setProvince(modelModel.getLocation().getProvince());
        location.setWard(modelModel.getLocation().getWard());
        profileDTO.setLocation(location);

        ModelBodyIndexModel bodyIndex = new ModelBodyIndexModel();
        bodyIndex.setBust(modelModel.getBust());
        bodyIndex.setHeight(modelModel.getHeight());
        bodyIndex.setHip(modelModel.getHip());
        bodyIndex.setWaist(modelModel.getWaist());
        bodyIndex.setWeight(modelModel.getWeight());
        profileDTO.setBodyIndex(bodyIndex);

        return profileDTO;
    }

    public String updateProfile(String id, ModelProfileModel profile) {
        AccountDTO aModel = new AccountDTO();
        ModelDTO mModel = new ModelDTO();
        firestore.collection("Account").document(id).set(aModel);
        firestore.collection("Model").document(id).set(mModel);
        return "";
    }

}
