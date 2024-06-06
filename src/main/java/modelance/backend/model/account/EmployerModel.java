package modelance.backend.model.account;

import java.util.Date;
import modelance.backend.model.work.Industry;
import modelance.backend.model.work.Location;

public class EmployerModel extends AccountModel {

    private String companyAddress;
    private String companyName;
    private String description;
    private Industry industry;
    private Location location;

    public EmployerModel() {

    }

    public EmployerModel(String username, String fullName, AccountGender gender, AccountRole role, AccountStatus status,
            String avatar, Date createDate, Date dateOfBirth, String password, String id, String email,
            String companyAddress, String companyName, String description, Industry industry, Location location) {
        super(username, fullName, gender, role, status, avatar, createDate, dateOfBirth, password, id, email);
        this.companyAddress = companyAddress;
        this.companyName = companyName;
        this.description = description;
        this.industry = industry;
        this.location = location;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
