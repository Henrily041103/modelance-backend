package modelance.backend.controller.model;

import org.springframework.web.bind.annotation.RestController;

import modelance.backend.model.JobModel;
import modelance.backend.service.model.ModelJobService;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/model/job")
public class ModelJobController {
    private final ModelJobService jobService;

    public ModelJobController(ModelJobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("")
    public List<JobModel> getAppliedJobs(Authentication authentication) {
        List<JobModel> result = null;

        try {
            result = jobService.getAppliedJobs(authentication);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    @PostMapping("apply/{id}")
    public JobModel applyForJob(Authentication authentication, @PathVariable String jobId) {
        JobModel result = null;

        try {
            result = jobService.applyForJob(authentication, jobId);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    @PostMapping("unapply/{id}")
    public JobModel unapplyForJob(Authentication authentication, @PathVariable String jobId) {
        JobModel result = null;

        try {
            result = jobService.unapplyJob(authentication, jobId);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }
}
