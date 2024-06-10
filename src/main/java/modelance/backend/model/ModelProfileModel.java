package modelance.backend.model;

import java.util.Date;

public class ModelProfileModel {
    private String id;
    private String username;
    private String fullName;
    private String email;
    private String avatar;
    private Date dateOfBirth;
    private String gender;
    private String accountStatus;
    private ModelBodyIndexModel bodyIndex;
    private String industry;
    private LocationModel location;

    public ModelProfileModel() {
    }

    public ModelProfileModel(String id, String username, String fullName, String email, String avatar, Date dateOfBirth,
            String gender, String accountStatus, ModelBodyIndexModel bodyIndex, String industry, LocationModel location) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.avatar = avatar;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.accountStatus = accountStatus;
        this.bodyIndex = bodyIndex;
        this.industry = industry;
        this.location = location;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public ModelBodyIndexModel getBodyIndex() {
        return bodyIndex;
    }

    public void setBodyIndex(ModelBodyIndexModel bodyIndex) {
        this.bodyIndex = bodyIndex;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

}
