package modelance.backend.dto;

import java.util.ArrayList;
import java.util.Date;

public class ContractDTO {
    private ArrayList<String> employerTerms;
    private ArrayList<String> modelTerms;
    private ModelDTO model;
    private long payment;
    private Date startTime;
    private Date endTime;
    private String job;
    private String status;

    public ContractDTO() {
    }

    public ContractDTO(ArrayList<String> employerTerms, ArrayList<String> modelTerms, ModelDTO model, long payment,
            Date startTime, Date endTime, String job, String status) {
        this.employerTerms = employerTerms;
        this.modelTerms = modelTerms;
        this.model = model;
        this.payment = payment;
        this.startTime = startTime;
        this.endTime = endTime;
        this.job = job;
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

    public ModelDTO getModel() {
        return model;
    }

    public void setModel(ModelDTO model) {
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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
