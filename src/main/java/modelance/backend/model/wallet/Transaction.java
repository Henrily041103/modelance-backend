package modelance.backend.model.wallet;

import java.util.Date;

import com.google.firebase.database.Exclude;

public class Transaction {
    @Exclude
    private String id;
    private String status;
    private Date datetime;
    private boolean isBank;
    private long amount;
    private Wallet wallet;

    public Transaction(String id, String status, Date datetime, boolean isBank, long amount, Wallet wallet) {
        this.id = id;
        this.status = status;
        this.datetime = datetime;
        this.isBank = isBank;
        this.amount = amount;
        this.wallet = wallet;
    }

    public Transaction() {
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public boolean isBank() {
        return isBank;
    }

    public void setBank(boolean isBank) {
        this.isBank = isBank;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
