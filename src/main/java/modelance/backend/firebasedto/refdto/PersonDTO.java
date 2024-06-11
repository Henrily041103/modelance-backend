package modelance.backend.firebasedto.refdto;

public class PersonDTO {
    private String id;
    private String fullName;

    public PersonDTO() {
    }

    public PersonDTO(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}