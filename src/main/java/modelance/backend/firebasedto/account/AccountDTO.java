package modelance.backend.firebasedto.account;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.cloud.firestore.annotation.Exclude;
import com.google.cloud.firestore.annotation.IgnoreExtraProperties;

@IgnoreExtraProperties
@JsonSubTypes({
        @JsonSubTypes.Type(value = ModelDTO.class, name = "model"),
        @JsonSubTypes.Type(value = EmployerDTO.class, name = "employer"),
})
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public class AccountDTO {
    private String username;
    private String fullName;
    private GenderDTO gender;
    private AccountRoleDTO role;
    private AccountStatusDTO status;
    private String avatar;
    private Date createDate;
    private Date dateOfBirth;
    private String password;
    private String id;
    private String email;

    public AccountDTO() {
    }

    public AccountDTO(String username, String fullName, GenderDTO gender, AccountRoleDTO role, AccountStatusDTO status,
            String avatar, Date createDate, Date dateOfBirth, String password, String id, String email) {
        this.username = username;
        this.fullName = fullName;
        this.gender = gender;
        this.role = role;
        this.status = status;
        this.avatar = avatar;
        this.createDate = createDate;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.id = id;
        this.email = email;
    }

    public AccountDTO(String username, String password, String id, String email) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.email = email;
    }

    public AccountDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AccountDTO(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AccountDTO other = (AccountDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public GenderDTO getGender() {
        return gender;
    }

    public AccountRoleDTO getRole() {
        return role;
    }

    public AccountStatusDTO getStatus() {
        return status;
    }

    public String getAvatar() {
        return avatar;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(GenderDTO gender) {
        this.gender = gender;
    }

    public void setRole(AccountRoleDTO role) {
        this.role = role;
    }

    public void setStatus(AccountStatusDTO status) {
        this.status = status;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AccountModel [username=" + username + ", fullName=" + fullName + ", gender=" + gender + ", role=" + role
                + ", status=" + status + ", avatar=" + avatar + ", createDate=" + createDate + ", dateOfBirth="
                + dateOfBirth + ", password=" + password + ", id=" + id + ", email=" + email + "]";
    }

    public void setId(String id) {
        this.id = id;
    }

    public void copyFrom(AccountDTO other) {
        if (other.getUsername() != null) {
            this.setUsername(other.getUsername());
        }
        if (other.getFullName() != null) {
            this.setFullName(other.getFullName());
        }
        if (other.getGender() != null) {
            this.setGender(other.getGender());
        }
        if (other.getRole() != null) {
            this.setRole(other.getRole());
        }
        if (other.getStatus() != null) {
            this.setStatus(other.getStatus());
        }
        if (other.getAvatar() != null) {
            this.setAvatar(other.getAvatar());
        }
        if (other.getCreateDate() != null) {
            this.setCreateDate(other.getCreateDate());
        }
        if (other.getDateOfBirth() != null) {
            this.setDateOfBirth(other.getDateOfBirth());
        }
        if (other.getPassword() != null) {
            this.setPassword(other.getPassword());
        }
        if (other.getId() != null) {
            this.setId(other.getId());
        }
        if (other.getEmail() != null) {
            this.setEmail(other.getEmail());
        }
    }
}
