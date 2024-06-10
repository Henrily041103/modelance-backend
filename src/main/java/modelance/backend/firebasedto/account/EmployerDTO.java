package modelance.backend.firebasedto.account;

import java.util.Date;

import modelance.backend.firebasedto.work.IndustryDTO;
import modelance.backend.firebasedto.work.LocationDTO;

public class EmployerDTO extends AccountDTO {

    private String companyName;
    private String description;
    private IndustryDTO industry;
    private LocationDTO location;

    public EmployerDTO() {

    }

    public EmployerDTO(String username, String fullName, GenderDTO gender, AccountRoleDTO role, AccountStatusDTO status,
            String avatar, Date createDate, Date dateOfBirth, String password, String id, String email,
            String companyName, String description, IndustryDTO industry, LocationDTO location) {
        super(username, fullName, gender, role, status, avatar, createDate, dateOfBirth, password, id, email);
        this.companyName = companyName;
        this.description = description;
        this.industry = industry;
        this.location = location;
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
