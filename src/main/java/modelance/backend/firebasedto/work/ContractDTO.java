package modelance.backend.firebasedto.work;

import java.util.ArrayList;
import java.util.Date;

import com.google.cloud.firestore.DocumentReference;

public class ContractDTO {
    private ArrayList<String> employerTerms;
    private ArrayList<String> modelTerms;
    private DocumentReference model;
    private long payment;
    private Date startTime;
    private Date endTime;
    private DocumentReference job;
    private DocumentReference status;

    public ContractDTO() {
    }

    public ContractDTO(ArrayList<String> employerTerms, ArrayList<String> modelTerms, DocumentReference model,
            long payment, Date startTime, Date endTime, DocumentReference job, DocumentReference status) {
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

    public DocumentReference getModel() {
        return model;
    }

    public void setModel(DocumentReference model) {
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

    public DocumentReference getJob() {
        return job;
    }

    public void setJob(DocumentReference job) {
        this.job = job;
    }

    public DocumentReference getStatus() {
        return status;
    }

    public void setStatus(DocumentReference status) {
        this.status = status;
    }

}
