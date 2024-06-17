package modelance.backend.controller.employer;

import org.springframework.web.bind.annotation.RestController;

import modelance.backend.firebasedto.work.ContractDTO;
import modelance.backend.firebasedto.work.JobDTO;
import modelance.backend.model.JobModel;
import modelance.backend.service.employer.EmployerJobService;
import modelance.backend.service.employer.NoJobExistsException;

import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/employer/job")
public class EmployerJobController {
    private final EmployerJobService jobService;

    public EmployerJobController(EmployerJobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("")
    public List<JobModel> getAllPostedJob(Authentication authentication)
            throws ExecutionException, InterruptedException {
        return jobService.getCurrentJobs(authentication);
    }

    @PostMapping("create")
    public String createNewJob(@RequestBody JobModel jobDTO) throws ExecutionException, InterruptedException {
        return jobService.createNewJob(jobDTO);
    }

    @GetMapping("{id}")
    public JobModel getPostedJobDetails(@PathVariable String id) {
        JobModel job = null;
        try {
            job = jobService.getPostedJobDetails(id);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return job;
    }

    @PutMapping("{id}/finish")
    public JobModel finishJob(@PathVariable String id) {
        JobModel job = null;
        try {
            job = jobService.updateJobStatus(id, "5");
        } catch (InterruptedException | ExecutionException | NoJobExistsException e) {
            e.printStackTrace();
        }
        return job;
    }

    @PutMapping("{id}/cancel")
    public JobModel cancelJob(@PathVariable String id) {
        JobModel job = null;
        try {
            job = jobService.updateJobStatus(id, "3");
        } catch (InterruptedException | ExecutionException | NoJobExistsException e) {
            e.printStackTrace();
        }
        return job;
    }

    @PutMapping("{id}/close")
    public JobModel closeJob(@PathVariable String id) {
        JobModel job = null;
        try {
            job = jobService.updateJobStatus(id, "2");
        } catch (InterruptedException | ExecutionException | NoJobExistsException e) {
            e.printStackTrace();
        }
        return job;
    }

    @PostMapping("{id}/approve")
    public ContractDTO approveApplicant(@PathVariable String id, @RequestBody String applicantId, @RequestBody JobDTO jobDTO) {
        ContractDTO contract = null;
        try {
            contract = jobService.approveApplicant(id, jobDTO);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return contract;
    }

}
