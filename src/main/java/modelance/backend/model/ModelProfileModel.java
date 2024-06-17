package modelance.backend.model;

import java.util.Date;
import java.util.List;

class AccountStatusModelProfileModel {
    private String id;
    private String statusName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

}

public class ModelProfileModel {
    private String id;
    private String username;
    private String fullName;
    private String email;
    private String avatar;
    private Date dateOfBirth;
    private String gender;
    private AccountStatusModelProfileModel status;
    private String description;
    private ModelBodyIndexModel bodyIndex;
    private IndustryModel industry;
    private LocationModel location;
    private List<String> compCard;
    private List<SocialMediaModel> socialMedia;
    private List<PortfolioModel> portfolio;

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

    public AccountStatusModelProfileModel getStatus() {
        return status;
    }

    public void setStatus(AccountStatusModelProfileModel accountStatus) {
        this.status = accountStatus;
    }

    public ModelBodyIndexModel getBodyIndex() {
        return bodyIndex;
    }

    public void setBodyIndex(ModelBodyIndexModel bodyIndex) {
        this.bodyIndex = bodyIndex;
    }

    public IndustryModel getIndustry() {
        return industry;
    }

    public void setIndustry(IndustryModel industry) {
        this.industry = industry;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    public List<String> getCompCard() {
        return compCard;
    }

    public void setCompCard(List<String> compCard) {
        this.compCard = compCard;
    }

    public List<PortfolioModel> getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(List<PortfolioModel> portfolio) {
        this.portfolio = portfolio;
    }

    public List<SocialMediaModel> getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(List<SocialMediaModel> socialMedia) {
        this.socialMedia = socialMedia;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
