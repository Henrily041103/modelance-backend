package modelance.backend.model;

import java.util.Date;

public class ReviewModel {
    private String content;
    private String contract;
    private Date datetime;
    private int rating;
    private EmployerModel reviewer;
    private ModelModel reviewee;

    public ReviewModel() {
    }

    public ReviewModel(String content, String contract, Date datetime, int rating, EmployerModel reviewer,
            ModelModel reviewee) {
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

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
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

    public EmployerModel getReviewer() {
        return reviewer;
    }

    public void setReviewer(EmployerModel reviewer) {
        this.reviewer = reviewer;
    }

    public ModelModel getReviewee() {
        return reviewee;
    }

    public void setReviewee(ModelModel reviewee) {
        this.reviewee = reviewee;
    }

}
