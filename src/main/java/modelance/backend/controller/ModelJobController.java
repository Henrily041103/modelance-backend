package modelance.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import modelance.backend.model.JobModel;
import modelance.backend.service.account.ModelJobService;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/job")
public class ModelJobController {
    private final ModelJobService jobService;

    public ModelJobController(ModelJobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/all")
    public ArrayList<JobModel> getAllJobs() throws ExecutionException, InterruptedException {
        return jobService.getJobListService();
    }

    @GetMapping("/details/{id}")
    public JobModel getJobDetails(@PathVariable String id) throws InterruptedException, ExecutionException {
        return jobService.getJobsById(id);
    }

}
