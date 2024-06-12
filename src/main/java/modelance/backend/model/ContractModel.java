package modelance.backend.model;

import java.util.ArrayList;
import java.util.Date;

public class ContractModel {
    private ArrayList<String> employerTerms;
    private ArrayList<String> modelTerms;
    private ModelModel model;
    private long payment;
    private Date startTime;
    private Date endTime;
    private JobModel job;
    private String status;

    public ContractModel() {
    }

    public ContractModel(ArrayList<String> employerTerms, ArrayList<String> modelTerms, ModelModel model, long payment,
            Date startTime, Date endTime, JobModel job, String status) {
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

    public JobModel getJob() {
        return job;
    }

    public void setJob(JobModel job) {
        this.job = job;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
