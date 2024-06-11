package modelance.backend.model;

public class JobStatusModel {
    private String id;
    private String statusName;

    public JobStatusModel(String id, String statusName) {
        this.id = id;
        this.statusName = statusName;
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
