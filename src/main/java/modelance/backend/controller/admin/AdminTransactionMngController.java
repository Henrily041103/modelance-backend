package modelance.backend.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import modelance.backend.firebasedto.wallet.BankTransactionDTO;
import modelance.backend.model.TransactionModel;
import modelance.backend.service.admin.AdminService;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/admin/transactions")
public class AdminTransactionMngController {
    private final AdminService service;

    public AdminTransactionMngController(AdminService service) {
        this.service = service;
    }

    @GetMapping("")
    public GetAllTransactionsResponse getAllTransactions() {
        ArrayList<TransactionModel> result = null;
        GetAllTransactionsResponse response = new GetAllTransactionsResponse();
        response.setMessage("Failed");

        try {
            result = service.getAllTransaction();
            if (result.size() > 0) {
                response.setTransactions(result);
                response.setMessage("Success");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return response;
    }

    @GetMapping("{id}")
    public GetTransactionByIdResponse getTransactionById(@PathVariable String id) {
        TransactionModel result = null;
        GetTransactionByIdResponse response = new GetTransactionByIdResponse();
        response.setMessage("Failed");

        try {
            result = service.getTransactionById(id);
            if (result != null) {
                response.setTransaction(result);
                response.setMessage("Success");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return response;
    }

    @GetMapping("bank")
    public GetAllBankTransactionsResponse getAllBankTransactions() throws InterruptedException, ExecutionException {
        ArrayList<BankTransactionDTO> result = null;
        GetAllBankTransactionsResponse response = new GetAllBankTransactionsResponse();
        response.setMessage("Failed");

        try {
            result = service.getAllBankTransaction();
            if (result.size() > 0) {
                response.setTransactions(result);
                response.setMessage("Success");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return response;
    }

    @GetMapping("bank/{id}")
    public GetBankTransactionByIdResponse getBankTransactionById(@PathVariable String id) {
        BankTransactionDTO result = null;
        GetBankTransactionByIdResponse response = new GetBankTransactionByIdResponse();
        response.setMessage("Failed");

        try {
            result = service.getBankTransactionById(id);
            if (result != null) {
                response.setTransaction(result);
                response.setMessage("Success");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return response;
    }
}
