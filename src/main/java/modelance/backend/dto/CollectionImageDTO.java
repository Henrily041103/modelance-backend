package modelance.backend.dto;

import java.util.Date;

public class CollectionImageDTO {
    private String imageURL;
    private String description;
    private Date date;

    public CollectionImageDTO() {
    }

    public CollectionImageDTO(String imageURL, String description, Date date) {
        this.imageURL = imageURL;
        this.description = description;
        this.date = date;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
