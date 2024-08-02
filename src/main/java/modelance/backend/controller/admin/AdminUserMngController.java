package modelance.backend.controller.admin;

import org.springframework.web.bind.annotation.RestController;

import modelance.backend.firebasedto.account.AccountDTO;
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
    public ArrayList<AccountDTO> getAllUsers() {
        ArrayList<AccountDTO> accounts = null;

        try {
            accounts = service.getAllUsers();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    @GetMapping("{id}")
    public AccountDTO getAccount(@PathVariable String id, @RequestParam String role){
        AccountDTO account = null;

        try {
            account = service.getAccountByIdRole(id, role);
        } catch (InterruptedException | ExecutionException | NoAccountExistsException e) {
            e.printStackTrace();
        }

        return account;
    }

    @PutMapping("{id}/ban")
    public String banAccount(@PathVariable String id) {
        String result = "";

        try {
            result = service.updateAccountStatus(id, "inactive");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return "Unsuccessful";
        }
        return result;
    }

    @PutMapping("{id}/unban")
    public String unbanAccount(@PathVariable String id) {
        String result = "";

        try {
            result = service.updateAccountStatus(id, "active");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return "Unsuccessful";
        }
        return result;
    }

}
