package modelance.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import modelance.backend.dto.JobDTO;
import modelance.backend.service.account.EmployerJobManagementService;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/jobmanager")
public class EmployerJobManagementController {
    private final EmployerJobManagementService jobService;

    public EmployerJobManagementController(EmployerJobManagementService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/create")
    public String createNewJob(@RequestBody JobDTO jobDTO) throws ExecutionException, InterruptedException {
        return jobService.createNewJob(jobDTO);
    }
    
    @GetMapping("/{id}")
    public ArrayList<JobDTO> getAllPostedJob(@PathVariable String id) throws ExecutionException, InterruptedException {
        return jobService.getAllPostedJob(id);
    }
    
    @GetMapping("/details/{id}")
    public JobDTO getPostedJobDetails(@PathVariable String id) throws InterruptedException, ExecutionException {
        return jobService.getPostedJobDetails(id);
    }

    @PutMapping("/finish/{id}")
    public String finishJob(@PathVariable String id) throws InterruptedException, ExecutionException {
        return jobService.updateJobStatus(id,"5");
    }

    @PutMapping("/cancel/{id}")
    public String cancelJob(@PathVariable String id) throws InterruptedException, ExecutionException {
        return jobService.updateJobStatus(id,"3");
    }

    @PutMapping("/close/{id}")
    public String closeJob(@PathVariable String id) throws InterruptedException, ExecutionException {
        return jobService.updateJobStatus(id,"4");
    }


}
