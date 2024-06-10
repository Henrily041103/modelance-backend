package modelance.backend.firebasedto.account;

public class GenderDTO {

    private String id;
    private String genderName;

    @Override
    public String toString() {
        return "AccountGender [id=" + id + ", genderName=" + genderName + "]";
    }

    public GenderDTO() {
    }

    public GenderDTO(String id, String genderName) {
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
