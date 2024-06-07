package modelance.backend.model.account;

import java.util.Date;

public class AccountModel {
    private String username;
    private String fullName;
    private AccountGender gender;
    private AccountRole role;
    private AccountStatus status;
    private String avatar;
    private Date createDate;
    private Date dateOfBirth;
    private String password;
    private String id;
    private String email;

    public AccountModel() {
    }

    public AccountModel(String username, String fullName, AccountGender gender, AccountRole role, AccountStatus status,
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

    public AccountModel(String username, String password, String id, String email) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.email = email;
    }

    public AccountModel(String username, String password) {
        this.username = username;
        this.password = password;
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
        AccountModel other = (AccountModel) obj;
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

    public AccountGender getGender() {
        return gender;
    }

    public AccountRole getRole() {
        return role;
    }

    public AccountStatus getStatus() {
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

    public void setGender(AccountGender gender) {
        this.gender = gender;
    }

    public void setRole(AccountRole role) {
        this.role = role;
    }

    public void setStatus(AccountStatus status) {
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

    public void clone(AccountModel account) {
        
    }
}
