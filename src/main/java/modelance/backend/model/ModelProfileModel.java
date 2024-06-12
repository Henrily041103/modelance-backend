package modelance.backend.model;

import java.util.Date;

import modelance.backend.firebasedto.account.AccountStatusDTO;
import modelance.backend.firebasedto.account.GenderDTO;
import modelance.backend.firebasedto.work.IndustryDTO;
import modelance.backend.firebasedto.work.LocationDTO;

public class ModelProfileModel {
    private String id;
    private String username;
    private String fullName;
    private String email;
    private String avatar;
    private Date dateOfBirth;
    private GenderDTO gender;
    private AccountStatusDTO status;
    private int bust;
    private int height;
    private int hip;
    private int waist;
    private int weight;
    private IndustryDTO industry;
    private LocationDTO location;

    public ModelProfileModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public GenderDTO getGender() {
        return gender;
    }

    public void setGender(GenderDTO gender) {
        this.gender = gender;
    }

    public AccountStatusDTO getStatus() {
        return status;
    }

    public void setStatus(AccountStatusDTO status) {
        this.status = status;
    }

    public int getBust() {
        return bust;
    }

    public void setBust(int bust) {
        this.bust = bust;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    
}
