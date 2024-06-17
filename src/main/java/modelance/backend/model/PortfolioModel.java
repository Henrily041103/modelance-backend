package modelance.backend.model;

import java.util.List;

public class PortfolioModel {
    private String id;
    private List<String> images;
    private String title;

    public PortfolioModel(String id, List<String> images, String title) {
        this.id = id;
        this.images = images;
        this.title = title;
    }

    public PortfolioModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
