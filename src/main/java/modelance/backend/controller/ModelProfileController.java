package modelance.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import modelance.backend.service.account.ModelProfileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/mproflie")
public class ModelProfileController {
    private final ModelProfileService profileService;

    public ModelProfileController(ModelProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    public String getProfile(@PathVariable String id) {
        return new String();
    }

    @PutMapping("update/{id}")
    public String updateProfile(@PathVariable String id, @RequestBody String entity) {
        return entity;
    }

    @GetMapping("/rating/{id}")
    public String getAllRating(@PathVariable String id) {
        return new String();
    }

    @GetMapping("/portfolio/{id}")
    public String getAllCollections(@RequestParam String id) {
        return new String();
    }

    @PostMapping("/collection/add")
    public String addCollection(@RequestBody String collection) {
        return collection;
    }
    
    @GetMapping("/collection/{colid}")
    public String getCollection(@RequestParam String colid) {
        return new String();
    }

    @PutMapping("/collection/update")
    public String updateCollectionDetails(@RequestBody String collectionData) {
        return collectionData;
    }

    @PutMapping("/collection/hide")
    public String hideCollection(@RequestBody String collectionID) {
        return collectionID;
    }

    @PostMapping("/collection/addimage")
    public String addImageToCollection(@RequestBody String image) {
        return image;
    }
    

}
