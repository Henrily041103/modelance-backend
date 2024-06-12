package modelance.backend.firebasedto.account;

import java.util.Date;

import modelance.backend.firebasedto.work.IndustryDTO;
import modelance.backend.firebasedto.work.LocationDTO;

public class ModelDTO extends AccountDTO{
    private IndustryDTO industry;
    private LocationDTO location;
    private int bust;
    private int hip;
    private int waist;
    private int weight;
    private int height;

    public ModelDTO() {
    }

    

    public ModelDTO(String username, String fullName, GenderDTO gender, AccountRoleDTO role, AccountStatusDTO status,
            String avatar, Date createDate, Date dateOfBirth, String password, String id, String email,
            IndustryDTO industry, LocationDTO location, int bust, int hip, int waist, int weight, int height) {
        super(username, fullName, gender, role, status, avatar, createDate, dateOfBirth, password, id, email);
        this.industry = industry;
        this.location = location;
        this.bust = bust;
        this.hip = hip;
        this.waist = waist;
        this.weight = weight;
        this.height = height;
    }

    public IndustryDTO getIndustry() {
        return industry;
    }

    public void setIndustry(IndustryDTO industry) {
        this.industry = industry;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public int getBust() {
        return bust;
    }

    public void setBust(int bust) {
        this.bust = bust;
    }

    public int getHip() {
        return hip;
    }

    public void setHip(int hip) {
        this.hip = hip;
    }

    public int getWaist() {
        return waist;
    }

    public void setWaist(int waist) {
        this.waist = waist;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
