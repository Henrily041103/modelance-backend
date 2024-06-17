package modelance.backend.firebasedto.account;

public class AccountRoleDTO {

    private String id;
    private String roleName;

    public AccountRoleDTO() {
    }

    public AccountRoleDTO(String id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public AccountRoleDTO(String roleName) {
        this.roleName = roleName;
        switch (roleName) {
            case "admin":
                this.id = "1";
                break;
            case "model":
                this.id = "2";
                break;
            case "employer":
                this.id = "3";
                break;
        }
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
