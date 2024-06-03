package modelance.backend.model.account;

public class AccountStatus {
    private String id;
    private String statusName;

    @Override
    public String toString() {
        return "AccountStatus [id=" + id + ", statusName=" + statusName + "]";
    }

    public AccountStatus() {
    }

    public AccountStatus(String id, String statusName) {
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
