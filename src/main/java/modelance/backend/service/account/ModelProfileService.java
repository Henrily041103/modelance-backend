package modelance.backend.service.account;

import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import modelance.backend.dto.LocationDTO;
import modelance.backend.dto.ModelBodyIndexDTO;
import modelance.backend.dto.ModelProfileDTO;
import modelance.backend.model.account.AccountModel;
import modelance.backend.model.account.ModelModel;

@Service
public class ModelProfileService {
    private Firestore firestore;

    public ModelProfileService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public ModelProfileDTO getProfile(String id) throws InterruptedException, ExecutionException {
        AccountModel accountModel = firestore.collection("Account").document(id).get().get()
                .toObject(AccountModel.class);
        ModelModel modelModel = firestore.collection("Model").document(id).get().get().toObject(ModelModel.class);

        ModelProfileDTO profileDTO = new ModelProfileDTO();
        profileDTO.setId(id);
        profileDTO.setUsername(accountModel.getUsername());
        profileDTO.setFullName(accountModel.getFullName());
        profileDTO.setEmail(accountModel.getEmail());
        profileDTO.setAvatar(accountModel.getAvatar());
        profileDTO.setDateOfBirth(accountModel.getDateOfBirth());
        profileDTO.setGender(accountModel.getGender().getGenderName());
        profileDTO.setAccountStatus(accountModel.getStatus().getStatusName());
        profileDTO.setIndustry(modelModel.getIndustry().getIndustryName());

        LocationDTO location = new LocationDTO();
        location.setAndress(modelModel.getLocation().getAddress());
        location.setDistrict(modelModel.getLocation().getDistrict());
        location.setProvince(modelModel.getLocation().getProvince());
        location.setWard(modelModel.getLocation().getWard());
        profileDTO.setLocation(location);

        ModelBodyIndexDTO bodyIndex = new ModelBodyIndexDTO();
        bodyIndex.setBust(modelModel.getBust());
        bodyIndex.setHeight(modelModel.getHeight());
        bodyIndex.setHip(modelModel.getHip());
        bodyIndex.setWaist(modelModel.getWaist());
        bodyIndex.setWeight(modelModel.getWeight());
        profileDTO.setBodyIndex(bodyIndex);

        return profileDTO;
    }

}
