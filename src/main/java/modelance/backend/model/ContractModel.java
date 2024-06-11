package modelance.backend.model;

import java.util.ArrayList;
import java.util.Date;

public class ContractModel {
    private ArrayList<String> employerTerms;
    private ArrayList<String> modelTerms;
    private Long payment;
    private Date startTime;
    private Date endTime;
    private Job job;
    private Model model;
    private Employer employer;
    private Status status;

    public class Job {
        private String id;
        private String title;

        public Job(String id, String title) {
            this.id = id;
            this.title = title;
        }

        public Job() {
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

    public class Model {
        private String id;
        private String fullName;

        public Model(String id, String fullName) {
            this.id = id;
            this.fullName = fullName;
        }

        public Model() {
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

    public class Employer {
        private String id;
        private String fullName;

        public Employer(String id, String fullName) {
            this.id = id;
            this.fullName = fullName;
        }

        public Employer() {
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

    public class Status {
        private String id;
        private String statusName;

        public Status(String id, String statusName) {
            this.id = id;
            this.statusName = statusName;
        }

        public Status() {
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

    public ContractModel() {
    }

    public ContractModel(ArrayList<String> employerTerms, ArrayList<String> modelTerms, Long payment, Date startTime,
            Date endTime, Job job, Model model, Employer employer, Status status) {
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

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
