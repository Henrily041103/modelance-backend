package modelance.backend.controller.employer;

import java.util.ArrayList;
import java.util.Date;

import modelance.backend.firebasedto.work.ContractDTO.EmployerDTO;
import modelance.backend.firebasedto.work.ContractDTO.JobDTO;
import modelance.backend.firebasedto.work.ContractDTO.ModelDTO;

class EmployerContractCreateRequest {
    private ArrayList<String> employerTerms;
    private ArrayList<String> modelTerms;
    private Long payment;
    private Date startTime;
    private Date endTime;
    private JobDTO job;
    private ModelDTO model;
    private EmployerDTO employer;
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
    
    
}
