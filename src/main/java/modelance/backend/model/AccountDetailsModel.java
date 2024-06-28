package modelance.backend.model;

import java.util.Date;

import modelance.backend.firebasedto.account.AccountRoleDTO;
import modelance.backend.firebasedto.account.AccountStatusDTO;
import modelance.backend.firebasedto.account.GenderDTO;

public class AccountDetailsModel {
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

    public AccountDetailsModel() {
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

    public GenderDTO getGender() {
        return gender;
    }

    public void setGender(GenderDTO gender) {
        this.gender = gender;
    }

    public AccountRoleDTO getRole() {
        return role;
    }

    public void setRole(AccountRoleDTO role) {
        this.role = role;
    }

    public AccountStatusDTO getStatus() {
        return status;
    }

    public void setStatus(AccountStatusDTO status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
