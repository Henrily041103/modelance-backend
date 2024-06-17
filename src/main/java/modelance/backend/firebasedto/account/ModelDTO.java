package modelance.backend.firebasedto.account;

import java.util.List;

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


public class ModelDTO extends AccountDTO {
    private IndustryDTO industry;
    private LocationDTO location;
    private List<PortfolioDTO> portfolio;
    private List<String> compCard;
    private List<SocialMediaModelDTO> socialMedia;
    private String description;
    private int bust;
    private int hip;
    private int waist;
    private int weight;
    private int height;

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

    public List<String> getCompCard() {
        return compCard;
    }

    public void setCompCard(List<String> compCard) {
        this.compCard = compCard;
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

}
