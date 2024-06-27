package modelance.backend.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import modelance.backend.model.ContractModel;
import modelance.backend.model.JobItemModel;
import modelance.backend.model.JobModel;
import modelance.backend.service.admin.AdminService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/admin/job")
public class AdminJobMngController {
    private final AdminService service;

    public AdminJobMngController(AdminService service) {
        this.service = service;
    }

    @GetMapping("")
    public ArrayList<JobItemModel> getAllJobs() throws InterruptedException, ExecutionException {
        return service.getAllJobs();
    }
    
    @GetMapping("/{doc}")
    public JobModel getJobDetails(@PathVariable String doc) throws InterruptedException, ExecutionException {
        return service.getJobDetails(doc);
    }
    
    // @PutMapping("approve/{doc}")
    // public String approveJobPosting(@PathVariable String doc) {
    //     return "entity";
    // }

    // @PutMapping("decline/{doc}")
    // public String declineJobPosting(@PathVariable String doc) {
    //     return "entity";
    // }

    @PutMapping("contract/{doc}")
    public ContractModel viewContractDetails(@PathVariable String doc) throws InterruptedException, ExecutionException {
        return service.getContractDetails(doc);
    }

}
