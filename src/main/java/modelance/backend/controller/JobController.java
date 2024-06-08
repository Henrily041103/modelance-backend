package modelance.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import modelance.backend.dto.JobDTO;
import modelance.backend.service.account.JobService;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/job")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/all")
    public ArrayList<JobDTO> getAllJobs() throws ExecutionException, InterruptedException {
        return jobService.getJobListService();
    }

}
