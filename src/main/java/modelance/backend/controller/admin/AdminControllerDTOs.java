package modelance.backend.controller.admin;

import java.util.ArrayList;

import modelance.backend.firebasedto.wallet.BankTransactionDTO;
import modelance.backend.model.TransactionModel;

class GetAllTransactionsResponse {
    private String message;
    private ArrayList<TransactionModel> transactions;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<TransactionModel> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<TransactionModel> transactions) {
        this.transactions = transactions;
    }
}

class GetTransactionByIdResponse {
    private String message;
    private TransactionModel transaction;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TransactionModel getTransaction() {
        return transaction; 
    }

    public void setTransaction(TransactionModel transaction) {
        this.transaction = transaction; 
    }
}

class GetAllBankTransactionsResponse {
    private String message;
    private ArrayList<BankTransactionDTO> transactions;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<BankTransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<BankTransactionDTO> transactions) {
        this.transactions = transactions;
    }
}

class GetBankTransactionByIdResponse {
    private String message;
    private BankTransactionDTO transaction;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BankTransactionDTO getTransaction() {
        return transaction;
    }

    public void setTransaction(BankTransactionDTO transaction) {
        this.transaction = transaction;
    }
}
