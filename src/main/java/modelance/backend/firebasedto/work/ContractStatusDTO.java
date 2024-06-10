package modelance.backend.firebasedto.work;

public class ContractStatusDTO {
    private String statusName;

    public ContractStatusDTO(String statusName) {
        this.statusName = statusName;
    }

    public ContractStatusDTO() {
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

}
