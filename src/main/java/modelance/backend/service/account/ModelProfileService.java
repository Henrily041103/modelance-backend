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
        AccountDTO accountDTO = firestore.collection("Account").document(id).get().get()
                .toObject(AccountDTO.class);
        ModelDTO modelDTO = firestore.collection("Model").document(id).get().get().toObject(ModelDTO.class);

        ModelProfileModel profileModel = new ModelProfileModel();
        profileModel.setId(id);
        profileModel.setUsername(accountDTO.getUsername());
        profileModel.setFullName(accountDTO.getFullName());
        profileModel.setEmail(accountDTO.getEmail());
        profileModel.setAvatar(accountDTO.getAvatar());
        profileModel.setDateOfBirth(accountDTO.getDateOfBirth());
        profileModel.setGender(accountDTO.getGender().getGenderName());
        profileModel.setAccountStatus(accountDTO.getStatus().getStatusName());
        profileModel.setIndustry(modelDTO.getIndustry().getIndustryName());

        LocationModel location = new LocationModel();
        location.setAndress(modelDTO.getLocation().getAddress());
        location.setDistrict(modelDTO.getLocation().getDistrict());
        location.setProvince(modelDTO.getLocation().getProvince());
        location.setWard(modelDTO.getLocation().getWard());
        profileModel.setLocation(location);

        ModelBodyIndexModel bodyIndex = new ModelBodyIndexModel();
        bodyIndex.setBust(modelDTO.getBust());
        bodyIndex.setHeight(modelDTO.getHeight());
        bodyIndex.setHip(modelDTO.getHip());
        bodyIndex.setWaist(modelDTO.getWaist());
        bodyIndex.setWeight(modelDTO.getWeight());
        profileModel.setBodyIndex(bodyIndex);

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
