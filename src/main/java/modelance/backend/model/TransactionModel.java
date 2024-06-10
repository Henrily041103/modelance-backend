package modelance.backend.model;

import java.util.Date;

public class TransactionModel {
    private WalletModel wallet;
    private String status;
    private Date datetime;
    private int amount;
    private boolean isBank;

    public WalletModel getWallet() {
        return wallet;
    }

    public void setWallet(WalletModel wallet) {
        this.wallet = wallet;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isBank() {
        return isBank;
    }

    public void setBank(boolean isBank) {
        this.isBank = isBank;
    }

}
