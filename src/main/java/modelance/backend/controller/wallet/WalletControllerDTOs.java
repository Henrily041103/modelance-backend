package modelance.backend.controller.wallet;

import modelance.backend.firebasedto.premium.PremiumPackRenewalDTO; 

class CreateLinkRequest {
    private int amount;
    private String description;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

class RenewPremiumPackResponse {
    private String message;
    private PremiumPackRenewalDTO data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PremiumPackRenewalDTO getData() {
        return data;
    }

    public void setData(PremiumPackRenewalDTO data) {
        this.data = data;
    }

}
