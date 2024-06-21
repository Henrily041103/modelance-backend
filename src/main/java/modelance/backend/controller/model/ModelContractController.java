package modelance.backend.controller.model;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import modelance.backend.firebasedto.work.ContractDTO;
import modelance.backend.service.employer.EmployerContractService;

@RestController
@RequestMapping("/model/contract")
public class ModelContractController {
    private final EmployerContractService employerContractService;

    public ModelContractController(EmployerContractService employerContractService, ObjectMapper objectMapper) {
        this.employerContractService = employerContractService;
    }

    @GetMapping("")
    public List<ContractDTO> getCurrentContracts(Authentication authentication) {
        List<ContractDTO> contracts = null;

        try {
            contracts = employerContractService.getCurrentContracts(authentication);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return contracts;
    }
}
