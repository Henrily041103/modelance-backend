package modelance.backend.firebasedto.work;

import java.util.ArrayList;
import java.util.Date;

public class ContractDTO {
    private ArrayList<String> employerTerms;
    private ArrayList<String> modelTerms;
    private Long payment;
    private Date startTime;
    private Date endTime;
    private JobDTO job;
    private ModelDTO model;
    private EmployerDTO employer;
    private StatusDTO status;

    public class JobDTO {
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

    public class ModelDTO {
        private String id;
        private String fullName;

        public ModelDTO(String id, String fullName) {
            this.id = id;
            this.fullName = fullName;
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

    public ContractDTO() {
    }

    public ContractDTO(ArrayList<String> employerTerms, ArrayList<String> modelTerms, Long payment, Date startTime,
            Date endTime, JobDTO job, ModelDTO model, EmployerDTO employer, StatusDTO status) {
        this.employerTerms = employerTerms;
        this.modelTerms = modelTerms;
        this.payment = payment;
        this.startTime = startTime;
        this.endTime = endTime;
        this.job = job;
        this.model = model;
        this.employer = employer;
        this.status = status;
    }

    public ArrayList<String> getEmployerTerms() {
        return employerTerms;
    }

    public void setEmployerTerms(ArrayList<String> employerTerms) {
        this.employerTerms = employerTerms;
    }

    public ArrayList<String> getModelTerms() {
        return modelTerms;
    }

    public void setModelTerms(ArrayList<String> modelTerms) {
        this.modelTerms = modelTerms;
    }

    public Long getPayment() {
        return payment;
    }

    public void setPayment(Long payment) {
        this.payment = payment;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public JobDTO getJob() {
        return job;
    }

    public void setJob(JobDTO job) {
        this.job = job;
    }

    public ModelDTO getModel() {
        return model;
    }

    public void setModel(ModelDTO model) {
        this.model = model;
    }

    public EmployerDTO getEmployer() {
        return employer;
    }

    public void setEmployer(EmployerDTO employer) {
        this.employer = employer;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

}
