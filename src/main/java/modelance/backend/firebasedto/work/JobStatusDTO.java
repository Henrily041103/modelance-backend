package modelance.backend.firebasedto.work;

public class JobStatusDTO {
    private String statusName;

    public JobStatusDTO(String statusName) {
        this.statusName = statusName;
    }

    public JobStatusDTO() {
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

}
