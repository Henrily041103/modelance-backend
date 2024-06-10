package modelance.backend.firebasedto.work;

public class JobCategoriesDTO {
    private String categoryName;

    public JobCategoriesDTO() {
    }

    public JobCategoriesDTO(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
