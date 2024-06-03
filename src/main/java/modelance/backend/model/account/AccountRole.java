package modelance.backend.model.account;

public class AccountRole {

    private String id;
    private String roleName;

    public AccountRole() {
    }

    public AccountRole(String id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public String getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public String toString() {
        return "AccountRole [id=" + id + ", roleName=" + roleName + "]";
    }
}
