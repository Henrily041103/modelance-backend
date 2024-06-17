package modelance.backend.firebasedto.account;

import java.util.List;

public class PortfolioDTO {
    private String id;
    private List<String> images;
    private String title;

    public PortfolioDTO(String id, List<String> images, String title) {
        this.id = id;
        this.images = images;
        this.title = title;
    }

    public PortfolioDTO() {
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