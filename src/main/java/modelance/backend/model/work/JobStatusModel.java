package modelance.backend.model.work;

public class JobStatusModel {
    private String statusName;

    public JobStatusModel(String statusName) {
        this.statusName = statusName;
    }

    public JobStatusModel() {
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

}
