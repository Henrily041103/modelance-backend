package modelance.backend.firebasedto.wallet;

import com.google.firebase.database.Exclude;

import modelance.backend.model.AccountModel;

public class WalletDTO {
    @Exclude
    private String id;
    private AccountModel account;
    private long balance;

    public WalletDTO() {
    }

    public WalletDTO(String id, AccountModel account, long balance) {
        this.id = id;
        this.account = account;
        this.balance = balance;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountModel getAccount() {
        return account;
    }

    public void setAccount(AccountModel account) {
        this.account = account;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
