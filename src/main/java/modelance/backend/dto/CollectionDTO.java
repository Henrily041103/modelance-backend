package modelance.backend.dto;

import java.util.ArrayList;
import java.util.Date;

public class CollectionDTO {
    private String collectionID;
    private String title;
    private String description;
    private Date date;
    private ArrayList<CollectionImageDTO> imageList;

    public CollectionDTO() {
    }

    public CollectionDTO(String collectionID, String title, String description, Date date,
            ArrayList<CollectionImageDTO> imageList) {
        this.collectionID = collectionID;
        this.title = title;
        this.description = description;
        this.date = date;
        this.imageList = imageList;
    }

    public String getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(String collectionID) {
        this.collectionID = collectionID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public ArrayList<CollectionImageDTO> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<CollectionImageDTO> imageList) {
        this.imageList = imageList;
    }

}
