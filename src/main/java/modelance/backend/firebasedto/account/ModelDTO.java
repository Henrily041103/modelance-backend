package modelance.backend.firebasedto.account;

import java.util.ArrayList;
import java.util.List;

import com.google.cloud.firestore.annotation.Exclude;

import modelance.backend.firebasedto.work.IndustryDTO;
import modelance.backend.firebasedto.work.LocationDTO;

class SocialMediaModelDTO {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

class CompCardModelDTO {
    String url;
    int id;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CompCardModelDTO() {
    }

    public CompCardModelDTO(String url, int id) {
        this.url = url;
        this.id = id;
    }

}

public class ModelDTO extends AccountDTO {
    private IndustryDTO industry;
    private LocationDTO location;
    private List<PortfolioDTO> portfolio;
    private List<CompCardModelDTO> compCard;
    private List<SocialMediaModelDTO> socialMedia;
    private List<String> category;
    private String description;
    private float rating;
    private double hourlyRate;
    private int bust;
    private int hip;
    private int waist;
    private int weight;
    private int height;
    private String hairColor;
    private String eyeColor;

    
    public ModelDTO() {
        portfolio = new ArrayList<>();
        compCard = new ArrayList<>();
        socialMedia = new ArrayList<>();
        category = new ArrayList<>();
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public IndustryDTO getIndustry() {
        return industry;
    }

    public void setIndustry(IndustryDTO industry) {
        this.industry = industry;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
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

    public List<PortfolioDTO> getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(List<PortfolioDTO> portfolio) {
        this.portfolio = portfolio;
    }

    public List<CompCardModelDTO> getCompCard() {
        return compCard;
    }

    public void setCompCard(List<CompCardModelDTO> compCard) {
        this.compCard = compCard;
    }

    public void setCompCardList(List<String> compCard) {
        List<CompCardModelDTO> compCardList = new ArrayList<>();
        for (int i = 0; i < compCard.size(); i++) {
            compCardList.add(new CompCardModelDTO(compCard.get(i), i + 1 + this.compCard.size()));
        }
        setCompCard(compCardList);
    }

    public List<SocialMediaModelDTO> getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(List<SocialMediaModelDTO> socialMedia) {
        this.socialMedia = socialMedia;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Exclude
    public float getRating() {
        return rating;
    }

    @Exclude
    public void setRating(float rating) {
        this.rating = rating;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

}
