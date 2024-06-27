package modelance.backend.controller.admin;

import org.springframework.web.bind.annotation.RestController;

import modelance.backend.model.AccountDetailsModel;
import modelance.backend.model.AccountModel;
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
    public ArrayList<AccountModel> getAllUsers(@RequestParam String param) throws InterruptedException, ExecutionException {
        return service.getAllUsers();
    }

    @GetMapping("/{doc}")
    public AccountDetailsModel getAccount(@PathVariable String doc) throws InterruptedException, ExecutionException {
        return service.getAccount(doc);
    }
    
    @PutMapping("/ban/{doc}")
    public String banAccount(@PathVariable String doc) throws InterruptedException, ExecutionException {
        return service.updateAccountStatus(doc, "inactive");
    }

    @PutMapping("/unban/{doc}")
    public String unbanAccount(@PathVariable String doc) throws InterruptedException, ExecutionException {
        return service.updateAccountStatus(doc, "active");
    }
    
}
