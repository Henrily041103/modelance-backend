package modelance.backend.controller.job;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import modelance.backend.model.JobModel;
import modelance.backend.service.job.JobService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/job")
public class JobController {
    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("")
    public List<JobModel> getAllJobs() {
        List<JobModel> result = null;

        try {
            result = jobService.getAllJobs();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    @GetMapping("{id}")
    public JobModel getJobDetail(@PathVariable String id) {
        JobModel result = null;

        try {
            result = jobService.getJobById(id);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

}
