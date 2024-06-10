package modelance.backend.dto;

import java.util.Date;

public class TransactionDTO {
    private WalletDTO wallet;
    private String status;
    private Date datetime;
    private int amount;
    private boolean isBank;

    public WalletDTO getWallet() {
        return wallet;
    }

    public void setWallet(WalletDTO wallet) {
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
