package modelance.backend.firebasedto.work;

import java.util.Date;

import modelance.backend.firebasedto.refdto.ContDTO;
import modelance.backend.firebasedto.refdto.PersonDTO;

public class ReviewDTO {
    private String content;
    private ContDTO contract;
    private Date datetime;
    private int rating;
    private PersonDTO reviewer;
    private PersonDTO reviewee;

    public ReviewDTO() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ContDTO getContract() {
        return contract;
    }

    public void setContract(ContDTO contract) {
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

    public PersonDTO getReviewer() {
        return reviewer;
    }

    public void setReviewer(PersonDTO reviewer) {
        this.reviewer = reviewer;
    }

    public PersonDTO getReviewee() {
        return reviewee;
    }

    public void setReviewee(PersonDTO reviewee) {
        this.reviewee = reviewee;
    }

}
