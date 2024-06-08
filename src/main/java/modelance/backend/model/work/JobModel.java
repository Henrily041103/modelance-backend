package modelance.backend.model.work;

import java.util.ArrayList;
import java.util.Date;

import com.google.cloud.firestore.DocumentReference;

public class JobModel {
    private String id;
    private String title;
    private ArrayList<String> imageURL;
    private String jobDescription;
    private Long payment;
    private Date startDate;
    private Date endDate;
    private DocumentReference status;
    private DocumentReference category;
    private DocumentReference employer;

    public JobModel() {
    }

    public JobModel(String id, String title, ArrayList<String> imageURL, String jobDescription, Long payment, Date startDate,
            Date endDate, DocumentReference status, DocumentReference category, DocumentReference employer) {
        this.id = id;
        this.title = title;
        this.imageURL = imageURL;
        this.jobDescription = jobDescription;
        this.payment = payment;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.category = category;
        this.employer = employer;
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

    public ArrayList<String> getImageURL() {
        return imageURL;
    }

    public void setImageURL(ArrayList<String> imageURL) {
        this.imageURL = imageURL;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDesctription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Long getPayment() {
        return payment;
    }

    public void setPayment(Long payment) {
        this.payment = payment;
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

    public DocumentReference getStatus() {
        return status;
    }

    public void setStatus(DocumentReference status) {
        this.status = status;
    }

    public DocumentReference getCategory() {
        return category;
    }

    public void setCategory(DocumentReference category) {
        this.category = category;
    }

    public DocumentReference getEmployer() {
        return employer;
    }

    public void setEmployer(DocumentReference employer) {
        this.employer = employer;
    }

}
