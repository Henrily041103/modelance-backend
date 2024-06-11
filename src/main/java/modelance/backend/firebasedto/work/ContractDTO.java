package modelance.backend.firebasedto.work;

import java.util.ArrayList;
import java.util.Date;

class ContractAccountDTO {
    
    private String id;
    private String fullName;

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

class ContractJobDTO {
    private String id;
    private String jobTitle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

}

class ContractStatusDTO {
    private String id;
    private String statusName;

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

public class ContractDTO {
    private ArrayList<String> employerTerms;
    private ArrayList<String> modelTerms;
    private ContractAccountDTO model;
    private ContractAccountDTO employer;
    private long payment;
    private Date startTime;
    private Date endTime;
    private ContractJobDTO job;
    private ContractStatusDTO status;

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

    public ContractAccountDTO getModel() {
        return model;
    }

    public void setModel(ContractAccountDTO model) {
        this.model = model;
    }

    public long getPayment() {
        return payment;
    }

    public void setPayment(long payment) {
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

    public ContractJobDTO getJob() {
        return job;
    }

    public void setJob(ContractJobDTO job) {
        this.job = job;
    }

    public ContractStatusDTO getStatus() {
        return status;
    }

    public void setStatus(ContractStatusDTO status) {
        this.status = status;
    }

    public ContractAccountDTO getEmployer() {
        return employer;
    }

    public void setEmployer(ContractAccountDTO employer) {
        this.employer = employer;
    }

}
