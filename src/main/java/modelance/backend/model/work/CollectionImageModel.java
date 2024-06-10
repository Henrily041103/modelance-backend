package modelance.backend.model.work;

import java.util.Date;
import com.google.cloud.firestore.DocumentReference;

public class CollectionImageModel {
    private DocumentReference collectionDocRef;
    private String imageURL;
    private String description;
    private Date date;

    public CollectionImageModel() {
    }

    public CollectionImageModel(DocumentReference collectionDocRef, String imageURL, String description, Date date) {
        this.collectionDocRef = collectionDocRef;
        this.imageURL = imageURL;
        this.description = description;
        this.date = date;
    }

    public DocumentReference getCollectionDocRef() {
        return collectionDocRef;
    }

    public void setCollectionDocRef(DocumentReference collectionDocRef) {
        this.collectionDocRef = collectionDocRef;
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
