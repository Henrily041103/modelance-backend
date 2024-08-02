package modelance.backend.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import modelance.backend.firebasedto.premium.PremiumPackDTO;
import modelance.backend.firebasedto.premium.PremiumPackRenewalDTO;
import modelance.backend.service.admin.AdminService;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/admin/premium")
public class AdminPremiumController {
    private final AdminService service;

    public AdminPremiumController(AdminService service) {
        this.service = service;
    }

    @GetMapping("")
    public GetAllPremiumPacksResponse getAllPremiumPacks() {
        ArrayList<PremiumPackDTO> result = null;
        GetAllPremiumPacksResponse response = new GetAllPremiumPacksResponse();
        response.setMessage("Failed");

        try {
            result = service.getPacks();
            if (result.size() > 0) {
                response.setPacks(result);
                response.setMessage("Success");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return response;
    }

    @GetMapping("renewals")
    public GetAllPremiumPackRenewalsResponse getAllPremiumPackRenewals() {
        ArrayList<PremiumPackRenewalDTO> result = null;
        GetAllPremiumPackRenewalsResponse response = new GetAllPremiumPackRenewalsResponse();
        response.setMessage("Failed");

        try {
            result = service.getPackRenewals();
            if (result.size() > 0) {
                response.setPacks(result);
                response.setMessage("Success");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return response;
    }
}
