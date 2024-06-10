package modelance.backend.firebasedto.work;

import java.util.Date;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.Exclude;

public class CollectionDetailsDTO {
    private String collectionID;
    private DocumentReference accountDocRef;
    private String title;
    private String description;
    private Date date;

    public CollectionDetailsDTO() {
    }

    public CollectionDetailsDTO(String collectionID, DocumentReference accountDocRef, String title, String description,
            Date date) {
        this.collectionID = collectionID;
        this.accountDocRef = accountDocRef;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    @Exclude
    public String getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(String collectionID) {
        this.collectionID = collectionID;
    }

    public DocumentReference getAccountDocRef() {
        return accountDocRef;
    }

    public void setAccountDocRef(DocumentReference accountDocRef) {
        this.accountDocRef = accountDocRef;
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

}
