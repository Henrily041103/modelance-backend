package modelance.backend.firebasedto.work;

import java.util.Date;
import java.util.List;

import com.google.cloud.firestore.annotation.Exclude;

public class JobDTO {
    private String id;
    private String title;
    private List<String> imageURL;
    private String jobDescription;
    private Long payment;
    private Date startDate;
    private Date endDate;
    private StatusDTO status;
    private CategoryDTO category;
    private EmployerDTO employer;
    private List<ModelDTO> applicants;

    class ModelDTO {
        private String id;
        private String fullName;
        private String avatar;

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

    }

    class StatusDTO {
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

    class CategoryDTO {
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

    class EmployerDTO {
        private String id;
        private String fullName;
        private String avatar;

        public EmployerDTO(String id, String fullName, String avatar) {
            this.id = id;
            this.fullName = fullName;
            this.avatar = avatar;
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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

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

    public List<String> getImageURL() {
        return imageURL;
    }

    public void setImageURL(List<String> imageURL) {
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

    public void setStatus(String id, String statusName) {
        this.status = new StatusDTO(id, statusName);
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

    public List<ModelDTO> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<ModelDTO> applicants) {
        this.applicants = applicants;
    }

}
