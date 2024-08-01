package modelance.backend.controller.admin;

import org.springframework.web.bind.annotation.RestController;

import modelance.backend.firebasedto.account.AccountDTO;
import modelance.backend.model.AccountModel;
import modelance.backend.service.account.NoAccountExistsException;
import modelance.backend.service.admin.AdminService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/admin/users")
public class AdminUserMngController {
    private final AdminService service;

    public AdminUserMngController(AdminService service) {
        this.service = service;
    }

    @GetMapping("")
    public ArrayList<AccountModel> getAllUsers() {
        ArrayList<AccountModel> accounts = null;

        try {
            accounts = service.getAllUsers();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    @GetMapping("{doc}")
    public AccountDTO getAccount(@PathVariable String doc, @RequestParam String role){
        AccountDTO account = null;

        try {
            account = service.getAccountByIdRole(doc, role);
        } catch (InterruptedException | ExecutionException | NoAccountExistsException e) {
            e.printStackTrace();
        }

        return account;
    }

    @PutMapping("{doc}/ban")
    public String banAccount(@PathVariable String doc) {
        String result = "";

        try {
            result = service.updateAccountStatus(doc, "inactive");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return "Unsuccessful";
        }
        return result;
    }

    @PutMapping("{doc}/unban")
    public String unbanAccount(@PathVariable String doc) {
        String result = "";

        try {
            result = service.updateAccountStatus(doc, "active");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return "Unsuccessful";
        }
        return result;
    }

}
