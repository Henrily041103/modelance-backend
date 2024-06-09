package modelance.backend.dto;

import java.util.Date;

public class ReviewDTO {
    private String content;
    private ContractDTO contract;
    private Date datetime;
    private int rating;
    private EmployerDTO reviewer;
    private ModelDTO reviewee;

    public ReviewDTO() {
    }

    public ReviewDTO(String content, ContractDTO contract, Date datetime, int rating, EmployerDTO reviewer,
            ModelDTO reviewee) {
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

    public ContractDTO getContract() {
        return contract;
    }

    public void setContract(ContractDTO contract) {
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

    public EmployerDTO getReviewer() {
        return reviewer;
    }

    public void setReviewer(EmployerDTO reviewer) {
        this.reviewer = reviewer;
    }

    public ModelDTO getReviewee() {
        return reviewee;
    }

    public void setReviewee(ModelDTO reviewee) {
        this.reviewee = reviewee;
    }

}
