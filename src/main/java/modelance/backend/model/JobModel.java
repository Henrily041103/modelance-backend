package modelance.backend.model;

import java.util.ArrayList;
import java.util.Date;

public class JobModel {
    private String id;
    private String title;
    private Long payment;
    private ArrayList<String> imageURL;
    private String category;
    private EmployerModel employer;
    private Date startDate;
    private Date endDate;
    private String jobDescription;
    private String status;

    public JobModel(String id, String title, Long payment, ArrayList<String> imageURL, String category, EmployerModel employer,
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

    public JobModel() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // {
    //     "title": "Hand model job",
    //     "payment": 50000000,
    //     "imageURL": [
    //         ""
    //     ],
    //     "category": "2",
    //     "employer": {
    //         "id": "YIkgMPNSJoecrgOo3tsn"
    //     },
    //     "startDate": "2024-07-24T17:00:00.912+00:00",
    //     "endDate": "2024-07-25T17:00:00.436+00:00",
    //     "jobDescription": " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio."
    // }

}
