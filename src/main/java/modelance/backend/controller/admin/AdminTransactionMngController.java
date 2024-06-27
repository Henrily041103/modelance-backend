package modelance.backend.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ArrayList<TransactionModel> getAllTransactions() throws InterruptedException, ExecutionException {
        return service.getAllTransaction();
    }
    
    @GetMapping("{doc}")
    public String getTransactionDetails(@PathVariable String doc) {
        return new String();
    }
    
}
