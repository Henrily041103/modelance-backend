package modelance.backend.controller.job;

import java.util.Date;
import java.util.List;

class ContractEditRequest {
    private List<String> employerTerms;
    private long payment;
    private Date startDate;
    private Date endDate;

    public List<String> getEmployerTerms() {
        return employerTerms;
    }

    public void setEmployerTerms(List<String> employerTerms) {
        this.employerTerms = employerTerms;
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

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}

class ContractStatusEditRequest {
    private String statusName;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

}