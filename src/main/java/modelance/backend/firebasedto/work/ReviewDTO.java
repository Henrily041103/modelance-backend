package modelance.backend.firebasedto.work;

import java.util.Date;

public class ReviewDTO {
    private String content;
    private ContractDTO contract;
    private Date datetime;
    private int rating;
    private AccountDTO reviewer;
    private AccountDTO reviewee;

    public class ContractDTO {
        private String id;

        public ContractDTO() {
        }

        public ContractDTO(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

    public class AccountDTO {
        private String id;
        private String fullName;

        public AccountDTO() {
        }

        public AccountDTO(String id, String fullName) {
            this.id = id;
            this.fullName = fullName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

    }

    public ReviewDTO() {
    }

    public ReviewDTO(String content, ContractDTO contract, Date datetime, int rating, AccountDTO reviewer,
            AccountDTO reviewee) {
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

    public AccountDTO getReviewer() {
        return reviewer;
    }

    public void setReviewer(AccountDTO reviewer) {
        this.reviewer = reviewer;
    }

    public AccountDTO getReviewee() {
        return reviewee;
    }

    public void setReviewee(AccountDTO reviewee) {
        this.reviewee = reviewee;
    }

}
