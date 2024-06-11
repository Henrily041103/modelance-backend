package modelance.backend.firebasedto.work;

import java.util.ArrayList;
import java.util.Date;
import modelance.backend.firebasedto.constant.StatusDTO;
import modelance.backend.firebasedto.refdto.PersonDTO;
import modelance.backend.firebasedto.refdto.SjobDTO;

public class ContractDTO {
    private ArrayList<String> employerTerms;
    private ArrayList<String> modelTerms;
    private Long payment;
    private Date startTime;
    private Date endTime;
    private SjobDTO job;
    private PersonDTO model;
    private PersonDTO employer;
    private StatusDTO status;

    public ContractDTO() {
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

    public SjobDTO getJob() {
        return job;
    }

    public void setJob(SjobDTO job) {
        this.job = job;
    }

    public PersonDTO getModel() {
        return model;
    }

    public void setModel(PersonDTO model) {
        this.model = model;
    }

    public PersonDTO getEmployer() {
        return employer;
    }

    public void setEmployer(PersonDTO employer) {
        this.employer = employer;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

}
