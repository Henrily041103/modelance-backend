package modelance.backend.controller.model;

import java.util.List;

import modelance.backend.model.IndustryModel;
import modelance.backend.model.LocationModel;
import modelance.backend.model.ModelBodyIndexModel;
import modelance.backend.model.SocialMediaModel;

class ModelProfileUpdateRequest {
    private String description;
    private ModelBodyIndexModel bodyModel;
    private IndustryModel industry;
    private LocationModel location;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ModelBodyIndexModel getBodyModel() {
        return bodyModel;
    }

    public void setBodyModel(ModelBodyIndexModel bodyModel) {
        this.bodyModel = bodyModel;
    }

    public IndustryModel getIndustry() {
        return industry;
    }

    public void setIndustry(IndustryModel industry) {
        this.industry = industry;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

}

class ModelProfileUpdateResponse {
    private boolean result;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

}

class AddImagesResponse {
    private List<String> url;
    private String message;

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

class ModelSocialMediaUpdateResponse {
    private List<SocialMediaModel> socialMedia;
    private boolean result;

    public List<SocialMediaModel> getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(List<SocialMediaModel> socialMedia) {
        this.socialMedia = socialMedia;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

}

class ModelCategoryUpdateResponse {
    private List<String> category;
    private boolean result;

    public List<String> getCategory() {
        return category;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

}

class ModelInfoRequest {
    private IndustryModel industry;
    private ModelBodyIndexModel bodyIndexModel;
    private LocationModel location;

    public IndustryModel getIndustry() {
        return industry;
    }

    public void setIndustry(IndustryModel industry) {
        this.industry = industry;
    }

    public ModelBodyIndexModel getBodyIndexModel() {
        return bodyIndexModel;
    }

    public void setBodyIndexModel(ModelBodyIndexModel bodyIndexModel) {
        this.bodyIndexModel = bodyIndexModel;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

}