package modelance.backend.firebasedto.wallet;

import java.util.List;

public class OrderTransactionDTO {

    private String id;
    private int orderCode;
    private double amount;
    private double amountPaid;
    private double amountRemaining;
    private String status;
    private String createdAt;
    private List<Transaction> transactions;
    private String cancellationReason;
    private String canceledAt;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getAmountRemaining() {
        return amountRemaining;
    }

    public void setAmountRemaining(double amountRemaining) {
        this.amountRemaining = amountRemaining;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public String getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(String canceledAt) {
        this.canceledAt = canceledAt;
    }

    public static class Transaction {

        private String reference;
        private double amount;
        private String accountNumber;
        private String description;
        private String transactionDateTime;
        private String virtualAccountName;
        private String virtualAccountNumber;
        private String counterAccountBankId;
        private String counterAccountBankName;
        private String counterAccountName;
        private String counterAccountNumber;

        // Getters and Setters

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTransactionDateTime() {
            return transactionDateTime;
        }

        public void setTransactionDateTime(String transactionDateTime) {
            this.transactionDateTime = transactionDateTime;
        }

        public String getVirtualAccountName() {
            return virtualAccountName;
        }

        public void setVirtualAccountName(String virtualAccountName) {
            this.virtualAccountName = virtualAccountName;
        }

        public String getVirtualAccountNumber() {
            return virtualAccountNumber;
        }

        public void setVirtualAccountNumber(String virtualAccountNumber) {
            this.virtualAccountNumber = virtualAccountNumber;
        }

        public String getCounterAccountBankId() {
            return counterAccountBankId;
        }

        public void setCounterAccountBankId(String counterAccountBankId) {
            this.counterAccountBankId = counterAccountBankId;
        }

        public String getCounterAccountBankName() {
            return counterAccountBankName;
        }

        public void setCounterAccountBankName(String counterAccountBankName) {
            this.counterAccountBankName = counterAccountBankName;
        }

        public String getCounterAccountName() {
            return counterAccountName;
        }

        public void setCounterAccountName(String counterAccountName) {
            this.counterAccountName = counterAccountName;
        }

        public String getCounterAccountNumber() {
            return counterAccountNumber;
        }

        public void setCounterAccountNumber(String counterAccountNumber) {
            this.counterAccountNumber = counterAccountNumber;
        }
    }
}
