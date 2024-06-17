package modelance.backend.model;

import java.util.Date;
import java.util.List;

public class JobModel {
    private String id;
    private String title;
    private Long payment;
    private List<String> imageURL;
    private String category;
    private EmployerModel employer;
    private Date startDate;
    private Date endDate;
    private String jobDescription;
    private JobStatusModel status;
    private List<ModelModel> applicants;

    public List<ModelModel> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<ModelModel> applicants) {
        this.applicants = applicants;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPayment() {
        return payment;
    }

    public void setPayment(Long payment) {
        this.payment = payment;
    }

    public List<String> getImageURL() {
        return imageURL;
    }

    public void setImageURL(List<String> imageURL) {
        this.imageURL = imageURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public EmployerModel getEmployer() {
        return employer;
    }

    public void setEmployer(EmployerModel employer) {
        this.employer = employer;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public JobStatusModel getStatus() {
        return status;
    }

    public void setStatus(JobStatusModel status) {
        this.status = status;
    }
    public void setStatus(String id, String statusName) {
        this.status = new JobStatusModel(id, statusName);
    }
}
