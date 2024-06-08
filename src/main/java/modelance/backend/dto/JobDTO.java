package modelance.backend.dto;

import java.util.ArrayList;

public class JobDTO {
    private String id;
    private String title;
    private Long payment;
    private ArrayList<String> imageURL;
    private String category;
    
    public JobDTO(String id, String title, Long payment, ArrayList<String> imageURL, String category) {
        this.id = id;
        this.title = title;
        this.payment = payment;
        this.imageURL = imageURL;
        this.category = category;
    }

    public JobDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPayment() {
        return payment;
    }

    public void setPayment(Long payment) {
        this.payment = payment;
    }

    public ArrayList<String> getImageURL() {
        return imageURL;
    }

    public void setImageURL(ArrayList<String> imageURL) {
        this.imageURL = imageURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
