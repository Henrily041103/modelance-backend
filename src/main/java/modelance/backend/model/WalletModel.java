package modelance.backend.model;

class WalletAccountModel {
    private String id;
    private String role;

    public WalletAccountModel(String id, String role) {
        this.id = id;
        this.role = role;
    }

    public WalletAccountModel() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

}

public class WalletModel {
    private WalletAccountModel account;
    private int balance;

    public WalletModel(String id, String role, int balance) {
        WalletAccountModel account = new WalletAccountModel(id, role);
        this.account = account;
        this.balance = balance;
    }

    public WalletModel() {
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public WalletAccountModel getAccount() {
        return account;
    }

    public void setAccount(WalletAccountModel account) {
        this.account = account;
    }

    public void setAccount(String id, String role) {
        this.account = new WalletAccountModel(id, role);
    }

}