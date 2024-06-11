package modelance.backend.firebasedto.work;

import java.util.ArrayList;
import java.util.Date;
import com.google.cloud.firestore.annotation.Exclude;
import modelance.backend.firebasedto.constant.CategoryDTO;
import modelance.backend.firebasedto.constant.StatusDTO;
import modelance.backend.firebasedto.refdto.PersonDTO;

public class JobDTO {
    private String id;
    private String title;
    private ArrayList<String> imageURL;
    private String jobDescription;
    private Long payment;
    private Date startDate;
    private Date endDate;
    private StatusDTO status;
    private CategoryDTO category;
    private PersonDTO employer;

    public JobDTO() {
    }

    @Exclude
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

    public void setJobDescription(String jobDescription) {
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

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public PersonDTO getEmployer() {
        return employer;
    }

    public void setEmployer(PersonDTO employer) {
        this.employer = employer;
    }

}
