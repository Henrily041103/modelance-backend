package modelance.backend.model;

import java.util.ArrayList;
import java.util.Date;

public class CollectionModel {
    private String collectionID;
    private String title;
    private String description;
    private Date date;
    private ArrayList<CollectionImageModel> imageList;

    public CollectionModel() {
    }

    public CollectionModel(String collectionID, String title, String description, Date date,
            ArrayList<CollectionImageModel> imageList) {
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

    public ArrayList<CollectionImageModel> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<CollectionImageModel> imageList) {
        this.imageList = imageList;
    }

}
