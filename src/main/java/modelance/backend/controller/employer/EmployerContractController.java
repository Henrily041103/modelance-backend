package modelance.backend.controller.employer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import modelance.backend.firebasedto.work.ContractDTO;

import modelance.backend.service.account.NoAccountExistsException;
import modelance.backend.service.employer.EmployerContractService;
import modelance.backend.service.employer.NoJobExistsException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/employer/contract")
public class EmployerContractController {
    private final EmployerContractService employerContractService;
    private final ObjectMapper objectMapper;

    public EmployerContractController(EmployerContractService employerContractService, ObjectMapper objectMapper) {
        this.employerContractService = employerContractService;
        this.objectMapper = objectMapper;
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

    @PostMapping("{id}/finish")
    public EmployerContractFinishResponse finishContract(@PathVariable String id) {
        EmployerContractFinishResponse transactions = null;

        try {
            Map<String, Object> result = employerContractService.finishContract(id);
            transactions = objectMapper.convertValue(result, EmployerContractFinishResponse.class);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (NoJobExistsException e) {
            e.printStackTrace();
        } catch (NoAccountExistsException e) {
            e.printStackTrace();
        }

        return transactions;
    }
    
}
