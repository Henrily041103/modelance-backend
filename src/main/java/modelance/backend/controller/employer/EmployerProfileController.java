package modelance.backend.controller.employer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import modelance.backend.firebasedto.account.EmployerDTO;
import modelance.backend.model.ReviewModel;
import modelance.backend.service.account.NoAccountExistsException;
import modelance.backend.service.employer.EmployerProfileService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/employer/profile")
public class EmployerProfileController {
    private final EmployerProfileService profileService;
    private final ObjectMapper objectMapper;

    public EmployerProfileController(EmployerProfileService profileService, ObjectMapper objectMapper) {
        this.profileService = profileService;
        this.objectMapper = objectMapper;
    }

    @PutMapping("update")
    public EmployerProfileUpdateResponse updateEmployer(@RequestBody EmployerDTO updates,
            Authentication authentication)
            throws InterruptedException, ExecutionException {
        EmployerProfileUpdateResponse response = new EmployerProfileUpdateResponse();
        response.setResult(false);

        try {
            Map<String, Object> updatesMap = objectMapper.convertValue(updates,
                    new TypeReference<Map<String, Object>>() {
                    });
            response.setResult(profileService.updateEmployerProfile(updatesMap, authentication));
        } catch (InterruptedException | ExecutionException | NoAccountExistsException e) {
            e.printStackTrace();
        }

        return response;
    }

    @GetMapping("review")
    public List<ReviewModel> getAllReview(Authentication authentication)
            throws InterruptedException, ExecutionException {
        return profileService.getAllReview(authentication);
    }

}
