package modelance.backend.model.work;

public class JobCategoriesModel {
    private String categoryName;

    public JobCategoriesModel() {
    }

    public JobCategoriesModel(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
