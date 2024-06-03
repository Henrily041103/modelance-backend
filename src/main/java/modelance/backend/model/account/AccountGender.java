package modelance.backend.model.account;

public class AccountGender {

    private String id;
    private String genderName;

    @Override
    public String toString() {
        return "AccountGender [id=" + id + ", genderName=" + genderName + "]";
    }

    public AccountGender() {
    }

    public AccountGender(String id, String genderName) {
        this.id = id;
        this.genderName = genderName;
    }

    public String getId() {
        return id;
    }

    public String getGenderName() {
        return genderName;
    }
}
