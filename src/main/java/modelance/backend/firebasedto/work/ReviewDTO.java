package modelance.backend.firebasedto.work;

import java.util.Date;
import com.google.cloud.firestore.DocumentReference;

public class ReviewDTO {
    private String content;
    private DocumentReference contract;
    private Date datetime;
    private int rating;
    private DocumentReference reviewer;
    private DocumentReference reviewee;

    public ReviewDTO() {
    }

    public ReviewDTO(String content, DocumentReference contract, Date datetime, int rating,
            DocumentReference reviewer, DocumentReference reviewee) {
        this.content = content;
        this.contract = contract;
        this.datetime = datetime;
        this.rating = rating;
        this.reviewer = reviewer;
        this.reviewee = reviewee;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DocumentReference getContract() {
        return contract;
    }

    public void setContract(DocumentReference contract) {
        this.contract = contract;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public DocumentReference getReviewer() {
        return reviewer;
    }

    public void setReviewer(DocumentReference reviewer) {
        this.reviewer = reviewer;
    }

    public DocumentReference getReviewee() {
        return reviewee;
    }

    public void setReviewee(DocumentReference reviewee) {
        this.reviewee = reviewee;
    }

}
