package modelance.backend.model.account;

import java.util.Date;

import modelance.backend.model.work.Industry;
import modelance.backend.model.work.Location;

public class ModelModel extends AccountModel{
    private Industry industry;
    private Location location;
    private int bust;
    private int hip;
    private int waist;
    private int weight;
    private int height;

    public ModelModel() {
    }

    

    public ModelModel(String username, String fullName, AccountGender gender, AccountRole role, AccountStatus status,
            String avatar, Date createDate, Date dateOfBirth, String password, String id, String email,
            Industry industry, Location location, int bust, int hip, int waist, int weight, int height) {
        super(username, fullName, gender, role, status, avatar, createDate, dateOfBirth, password, id, email);
        this.industry = industry;
        this.location = location;
        this.bust = bust;
        this.hip = hip;
        this.waist = waist;
        this.weight = weight;
        this.height = height;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getBust() {
        return bust;
    }

    public void setBust(int bust) {
        this.bust = bust;
    }

    public int getHip() {
        return hip;
    }

    public void setHip(int hip) {
        this.hip = hip;
    }

    public int getWaist() {
        return waist;
    }

    public void setWaist(int waist) {
        this.waist = waist;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
