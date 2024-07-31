package modelance.backend.controller.model;

import java.util.ArrayList;
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
    private String eyeColor;
    private String hairColor;
    private Float hourlyRate;

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

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public Float getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(float hourlyRate) {
        this.hourlyRate = hourlyRate;
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

class ModelSocialMediaUpdateRequest {
    private List<SocialMediaModel> updates;

    public List<SocialMediaModel> getUpdates() {
        return updates;
    }

    public void setUpdates(List<SocialMediaModel> socialMedia) {
        this.updates = socialMedia;
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

class CategoryRequestObject {
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

class ModelCategoryUpdateRequest {
    private List<CategoryRequestObject> updates;

    public List<CategoryRequestObject> getUpdates() {
        return updates;
    }

    public void setUpdates(List<CategoryRequestObject> updates) {
        this.updates = updates;
    }

    public List<String> getList() {
        List<String> list = new ArrayList<>();
        for (CategoryRequestObject cat : updates) {
            list.add(cat.getCategory());
        }
        return list;
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