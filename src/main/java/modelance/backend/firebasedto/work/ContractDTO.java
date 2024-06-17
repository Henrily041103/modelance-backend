package modelance.backend.firebasedto.work;

import java.util.List;
import java.util.Date;

public class ContractDTO {
    private List<String> employerTerms;
    private List<String> modelTerms;
    private Long payment;
    private Date startDate;
    private Date endDate;
    private JobDTO job;
    private ModelDTO model;
    private EmployerDTO employer;
    private StatusDTO status;

    class JobDTO {
        private String id;
        private String title;

        public JobDTO(String id, String title) {
            this.id = id;
            this.title = title;
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

    }

    class ModelDTO {
        private String id;
        private String fullName;
        private String avatar;

        public ModelDTO(String id, String fullName, String avatar) {
            this.id = id;
            this.fullName = fullName;
            this.avatar = avatar;
        }

        public ModelDTO() {
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

    public List<String> getEmployerTerms() {
        return employerTerms;
    }

    public void setEmployerTerms(List<String> employerTerms) {
        this.employerTerms = employerTerms;
    }

    public List<String> getModelTerms() {
        return modelTerms;
    }

    public void setModelTerms(List<String> modelTerms) {
        this.modelTerms = modelTerms;
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

    public void setStartDate(Date startTime) {
        this.startDate = startTime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endTime) {
        this.endDate = endTime;
    }

    public JobDTO getJob() {
        return job;
    }

    public void setJob(JobDTO job) {
        this.job = job;
    }

    public void setJob(String id, String title) {
        this.job = new JobDTO(id, title);
    }

    public ModelDTO getModel() {
        return model;
    }

    public void setModel(ModelDTO model) {
        this.model = model;
    }

    public void setModel(String id, String fullName, String avatar) {
        this.model = new ModelDTO(id, fullName, avatar);
    }

    public EmployerDTO getEmployer() {
        return employer;
    }

    public void setEmployer(EmployerDTO employer) {
        this.employer = employer;
    }

    public void setEmployer(String fullName, String id, String avatar) {
        this.employer = new EmployerDTO(id, fullName, avatar);
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

}
