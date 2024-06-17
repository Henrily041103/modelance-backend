package modelance.backend.controller.employer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import modelance.backend.model.ContractModel;
import modelance.backend.service.employer.EmployerContractService;

import java.util.concurrent.ExecutionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/contract")
public class EmployerContractController {
    private final EmployerContractService contractService;

    public EmployerContractController(EmployerContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping("/{id}")
    public ContractModel viewContract(@PathVariable String id) throws InterruptedException, ExecutionException {
        return contractService.viewContract(id);
    }

    @PutMapping("/rescind/{id}")
    public String rescindContract(@PathVariable String id) {
        return "entity";
    }

}
