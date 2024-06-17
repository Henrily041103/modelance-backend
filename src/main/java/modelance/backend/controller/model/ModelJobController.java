package modelance.backend.controller.model;

import org.springframework.web.bind.annotation.RestController;

import modelance.backend.model.JobModel;
import modelance.backend.service.model.ModelJobService;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/model/job")
public class ModelJobController {
    private final ModelJobService jobService;

    public ModelJobController(ModelJobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("")
    public ArrayList<JobModel> getAllJobs() throws ExecutionException, InterruptedException {
        return jobService.getAllJobs();
    }

    @GetMapping("{id}")
    public JobModel getJobDetails(@PathVariable String id) throws InterruptedException, ExecutionException {
        return jobService.getJobsById(id);
    }

}
