package modelance.backend.model.work;

public class ContractStatusModel {
    private String statusName;

    public ContractStatusModel(String statusName) {
        this.statusName = statusName;
    }

    public ContractStatusModel() {
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

}
