package modelance.backend.dto;

import java.util.ArrayList;
import java.util.Date;

public class JobDTO {
    private String id;
    private String title;
    private Long payment;
    private ArrayList<String> imageURL;
    private String category;
    private EmployerDTO employer;
    private Date startDate;
    private Date endDate;
    private String jobDescription;
    private String status;

    public JobDTO(String id, String title, Long payment, ArrayList<String> imageURL, String category, EmployerDTO employer,
            Date startDate, Date endDate, String jobDescription, String status) {
        this.id = id;
        this.title = title;
        this.payment = payment;
        this.imageURL = imageURL;
        this.category = category;
        this.employer = employer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.jobDescription = jobDescription;
        this.status = status;
    }

    public JobDTO() {
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

    public ArrayList<String> getImageURL() {
        return imageURL;
    }

    public void setImageURL(ArrayList<String> imageURL) {
        this.imageURL = imageURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public EmployerDTO getEmployer() {
        return employer;
    }

    public void setEmployer(EmployerDTO employer) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
