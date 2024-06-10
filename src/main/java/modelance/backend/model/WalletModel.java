package modelance.backend.model;

class WalletAccountDTO {
    private String id;
    private String role;

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
    private String id;
    private WalletAccountDTO account;
    private int balance;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public WalletAccountDTO getAccount() {
        return account;
    }

    public void setAccount(WalletAccountDTO account) {
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}