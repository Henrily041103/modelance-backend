package modelance.backend.firebasedto.work;

import java.util.ArrayList;
import java.util.Date;
import com.google.cloud.firestore.annotation.Exclude;

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
    private EmployerDTO employer;

    public class StatusDTO {
        private String id;
        private String statusName;

        public StatusDTO(String id, String statusName) {
            this.id = id;
            this.statusName = statusName;
        }

        public StatusDTO() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

    }

    public class CategoryDTO {
        private String id;
        private String categoryName;

        public CategoryDTO(String id, String categoryName) {
            this.id = id;
            this.categoryName = categoryName;
        }

        public CategoryDTO() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

    }

    public class EmployerDTO {
        private String id;
        private String fullName;

        public EmployerDTO(String id, String fullName) {
            this.id = id;
            this.fullName = fullName;
        }

        public EmployerDTO() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

    }

    public JobDTO() {
    }

    public JobDTO(String id, String title, ArrayList<String> imageURL, String jobDescription, Long payment,
            Date startDate, Date endDate, StatusDTO status, CategoryDTO category, EmployerDTO employer) {
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

    public EmployerDTO getEmployer() {
        return employer;
    }

    public void setEmployer(EmployerDTO employer) {
        this.employer = employer;
    }

}
