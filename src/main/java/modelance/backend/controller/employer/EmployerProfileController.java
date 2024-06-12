package modelance.backend.controller.employer;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import modelance.backend.firebasedto.account.EmployerDTO;
import modelance.backend.model.ReviewModel;
import modelance.backend.service.account.AccountService;
import modelance.backend.service.account.NoAccountExistsException;
import modelance.backend.service.account.EmployerProfileService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/eprofile")
public class EmployerProfileController {
    private final EmployerProfileService profileService;
    private final AccountService accountService;

    public EmployerProfileController(EmployerProfileService profileService, AccountService accountService) {
        this.profileService = profileService;
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public EmployerDTO getEmployer(@PathVariable String id) {
        EmployerDTO employerAccount = null;
        try {
            employerAccount = accountService.loadEmployerModel(id);
        } catch (InterruptedException | ExecutionException | NoAccountExistsException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return employerAccount;
    }

    @PutMapping("/update")
    public String updateEmployer(@RequestBody EmployerDTO employerModel)
            throws InterruptedException, ExecutionException {
        return profileService.updateEmployerProfile(employerModel);
    }

    @GetMapping("/review/{id}")
    public ArrayList<ReviewModel> getAllReview(@PathVariable String id) throws InterruptedException, ExecutionException {
        return profileService.getAllReview(id);
    }

}
