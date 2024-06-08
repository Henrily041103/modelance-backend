package modelance.backend.controller;

import java.util.concurrent.ExecutionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import modelance.backend.model.account.EmployerModel;
import modelance.backend.service.account.AccountService;
import modelance.backend.service.account.NoAccountExistsException;
import modelance.backend.service.account.ProfileService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/profile")
public class EmployerProfileController {
    private final ProfileService profileService;
    private final AccountService accountService;

    public EmployerProfileController(ProfileService profileService, AccountService accountService) {
        this.profileService = profileService;
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public EmployerModel getEmployer(@PathVariable String id) {
        EmployerModel employerAccount = null;
        try {
            employerAccount = accountService.loadEmployerModel(id);
        } catch (InterruptedException | ExecutionException | NoAccountExistsException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return employerAccount;
    }

    @PutMapping("/update")
    public String updateEmployer(@RequestBody EmployerModel employerModel) throws InterruptedException, ExecutionException {
        return profileService.updateEmployerProfile(employerModel);
    }
    
}
