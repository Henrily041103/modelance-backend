package modelance.backend.firebasedto.premium;

import com.google.cloud.firestore.annotation.Exclude;

import modelance.backend.firebasedto.account.AccountRoleDTO;

public class PremiumPackDTO {
    @Exclude
    private String id;
    private String packName;
    private int packPrice;
    private AccountRoleDTO role;

    public PremiumPackDTO(String id, String packName, int packPrice, AccountRoleDTO role) {
        this.id = id;
        this.packName = packName;
        this.packPrice = packPrice;
        this.role = role;
    }

    public PremiumPackDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public int getPackPrice() {
        return packPrice;
    }

    public void setPackPrice(int packPrice) {
        this.packPrice = packPrice;
    }

    public AccountRoleDTO getRole() {
        return role;
    }

    public void setRole(AccountRoleDTO role) {
        this.role = role;
    }

}
