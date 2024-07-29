package modelance.backend.controller.model;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import modelance.backend.model.ReviewModel;
import modelance.backend.model.SocialMediaModel;
import modelance.backend.service.model.ModelProfileService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/model/profile")
public class ModelProfileController {
    private final ModelProfileService profileService;

    public ModelProfileController(
            ModelProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("update")
    public ModelProfileUpdateResponse updateModel(@RequestBody ModelProfileUpdateRequest updates,
            Authentication authentication)
            throws InterruptedException, ExecutionException {
        ModelProfileUpdateResponse response = new ModelProfileUpdateResponse();
        response.setResult(false);

        try {
            response.setResult(profileService.updateModelWorkInfo(authentication, updates.getDescription(),
                    updates.getBodyModel(), updates.getIndustry(), updates.getLocation()));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return response;
    }

    @PostMapping("social-media/update")
    public ModelSocialMediaUpdateResponse updateSocialMedia(@RequestBody List<SocialMediaModel> updates,
            Authentication authentication)
            throws InterruptedException, ExecutionException {
        ModelSocialMediaUpdateResponse response = new ModelSocialMediaUpdateResponse();
        response.setResult(false);

        try {
            List<SocialMediaModel> newList = profileService.updateSocialMedia(authentication, updates);
            if (!newList.isEmpty()) {
                response.setResult(true);
                response.setSocialMedia(newList);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return response;
    }

    @PostMapping("category/update")
    public ModelCategoryUpdateResponse updateCategory(@RequestBody List<String> updates,
            Authentication authentication)
            throws InterruptedException, ExecutionException {
        ModelCategoryUpdateResponse response = new ModelCategoryUpdateResponse();
        response.setResult(false);

        try {
            List<String> newList = profileService.updateCategory(authentication, updates);
            if (!newList.isEmpty()) {
                response.setResult(true);
                response.setCategory(newList);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return response;
    }

    @GetMapping("review")
    public List<ReviewModel> getAllReview(Authentication authentication)
            throws InterruptedException, ExecutionException {
        return profileService.getAllReview(authentication);
    }

    @PostMapping(path = "compcard/update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public AddImagesResponse changeCompCard(
            @RequestParam("file") MultipartFile[] files,
            Authentication authentication) {
        AddImagesResponse response = new AddImagesResponse();
        response.setMessage("failed");
        try {
            List<String> urlList = profileService.updateCompCard(files, authentication);
            if (urlList != null && urlList.size() > 0) {
                response.setMessage("success");
                response.setUrl(urlList);
            }
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return response;
    }

    @PostMapping(path = "portfolio/update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public AddImagesResponse changeAvatar(
            @RequestParam("file") MultipartFile[] files,
            @RequestParam String title,
            Authentication authentication) {
        AddImagesResponse response = new AddImagesResponse();
        response.setMessage("failed");
        try {
            List<String> urlList = profileService.updatePortfolio(files, authentication, title);
            if (urlList != null && urlList.size() > 0) {
                response.setMessage("success");
                response.setUrl(urlList);
            }
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return response;
    }
}
