package modelance.backend.dto;

import modelance.backend.model.account.AccountModel;
import modelance.backend.model.account.AccountRole;

public class AccountDTO {
    private String id;
    private String username;
    private String fullName;
    private AccountRole role;
    private String avatar;

    public AccountDTO(String id, String username, String fullName, AccountRole role, String avatar) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.role = role;
        this.avatar = avatar;
    }

    public AccountDTO() {
    }

    public AccountDTO(AccountModel model) {
        this.id = model.getId();
        this.username = model.getUsername();
        this.fullName = model.getFullName();
        this.role = model.getRole();
        this.avatar = model.getAvatar();
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

    public AccountRole getRole() {
        return role;
    }

    public void setRole(AccountRole role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}