package modelance.backend.firebasedto.work;

import com.google.cloud.firestore.annotation.IgnoreExtraProperties;

@IgnoreExtraProperties
public class IndustryDTO {
    private String id;
    private String industryName;
    
    public IndustryDTO(String id, String industryName) {
        this.id = id;
        this.industryName = industryName;
    }

    public IndustryDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

}
