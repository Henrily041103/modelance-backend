package modelance.backend.model;

import java.util.Date;
import java.util.List;

public class ContractModel {
    private List<String> employerTerms;
    private List<String> modelTerms;
    private ModelModel model;
    private long payment;
    private Date startDate;
    private Date endDate;
    private JobModel job;
    private StatusDTO status;

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

    public ModelModel getModel() {
        return model;
    }

    public void setModel(ModelModel model) {
        this.model = model;
    }

    public long getPayment() {
        return payment;
    }

    public void setPayment(long payment) {
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

    public JobModel getJob() {
        return job;
    }

    public void setJob(JobModel job) {
        this.job = job;
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
