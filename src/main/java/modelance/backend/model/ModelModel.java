package modelance.backend.model;

import modelance.backend.firebasedto.account.AccountRoleDTO;

public class ModelModel {
    // {id:"",username:''}
    private String id;
    private String username;
    private String fullName;
    private AccountRoleDTO role;
    private String avatar;

    public ModelModel() {
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

    public AccountRoleDTO getRole() {
        return role;
    }

    public void setRole(AccountRoleDTO role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
