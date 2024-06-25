package modelance.backend.firebasedto.wallet;

import com.google.firebase.database.Exclude;

import modelance.backend.firebasedto.account.AccountDTO;

public class WalletDTO {
    @Exclude
    private String id;
    private AccountDTO account;
    private long balance;

    public WalletDTO() {
    }

    public WalletDTO(String id, AccountDTO account, long balance) {
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

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
