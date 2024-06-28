package modelance.backend.model;

import java.util.Date;

class TransactionAccountModel {
    private String id;
    private String role;

    public TransactionAccountModel(String id, String role) {
        this.id = id;
        this.role = role;
    }

    public TransactionAccountModel() {
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

class TransactionWalletModel {
    private String id;
    private TransactionAccountModel account;

    public TransactionWalletModel(String id, String role) {
        TransactionAccountModel account = new TransactionAccountModel(id, role);
        this.account = account;
    }

    public TransactionWalletModel() {
    }

    public TransactionAccountModel getAccount() {
        return account;
    }

    public void setAccount(TransactionAccountModel account) {
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

public class TransactionModel {
    private TransactionWalletModel wallet;
    private String status;
    private Date datetime;
    private int orderCode;
    private int amount;
    private boolean isBank;

    public TransactionWalletModel getWallet() {
        return wallet;
    }

    public void setWallet(TransactionWalletModel wallet) {
        this.wallet = wallet;
    }

    public void setWallet(String walletId, String userId, String role) {
        this.wallet = new TransactionWalletModel(userId, role);
        this.wallet.setId(walletId);
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

    public long getAmount() {
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

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderName) {
        this.orderCode = orderName;
    }

    public String getWalletId() {
        return this.wallet.getId();
    }

}
