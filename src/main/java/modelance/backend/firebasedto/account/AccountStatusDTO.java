package modelance.backend.firebasedto.account;

public class AccountStatusDTO {
    private String id;
    private String statusName;

    @Override
    public String toString() {
        return "AccountStatus [id=" + id + ", statusName=" + statusName + "]";
    }

    public AccountStatusDTO() {
    }

    public AccountStatusDTO(String id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public String getId() {
        return id;
    }

    public String getStatusName() {
        return statusName;
    }
}
