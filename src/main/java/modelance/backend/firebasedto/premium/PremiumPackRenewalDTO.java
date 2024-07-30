package modelance.backend.firebasedto.premium;

import java.util.Date;

import com.google.cloud.firestore.annotation.Exclude;

class PackAccountDTO {
    private String id;
    private String role;

    public PackAccountDTO(String id, String role) {
        this.id = id;
        this.role = role;
    }

    public PackAccountDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}

public class PremiumPackRenewalDTO {
    @Exclude
    private String id;
    private PackAccountDTO account;
    private String packId;
    private Date renewalDate;

    public PremiumPackRenewalDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PackAccountDTO getAccount() {
        return account;
    }

    public void setAccount(PackAccountDTO account) {
        this.account = account;
    }

    public void setAccount(String id, String role) {
        this.account = new PackAccountDTO(id, role);
    }

    public String getPackId() {
        return packId;
    }

    public void setPackId(String packId) {
        this.packId = packId;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

}
