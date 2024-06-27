package modelance.backend.model;

import java.util.Date;

public class JobItemModel {
    private String id;
    private String title;
    private String category;
    private Date startDate;
    private JobStatusModel status;

    public JobItemModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public JobStatusModel getStatus() {
        return status;
    }

    public void setStatus(JobStatusModel status) {
        this.status = status;
    }

}
