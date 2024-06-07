package modelance.backend.model.work;

public class Industry {
    private String id;
    private String industryName;
    
    public Industry() {
    }
    public Industry(String id, String industryName) {
        this.id = id;
        this.industryName = industryName;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIndustryName() {
        return industryName;
    }
    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }
    
}
