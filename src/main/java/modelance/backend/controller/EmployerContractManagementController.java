package modelance.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import modelance.backend.dto.ContractDTO;
import modelance.backend.model.work.ContractModel;
import modelance.backend.service.account.EmployerContractManagementService;

import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/contract")
public class EmployerContractManagementController {
    private final EmployerContractManagementService contractService;

    public EmployerContractManagementController(EmployerContractManagementService contractService) {
        this.contractService = contractService;
    }

    @GetMapping("/{id}")
    public ContractDTO viewContract(@PathVariable String id) throws InterruptedException, ExecutionException {
        return contractService.viewContract(id);
    }

    @PostMapping("/add")
    public ContractModel addContract(@RequestBody ContractDTO contractDTO) throws InterruptedException, ExecutionException {
        return contractService.addContract(contractDTO);
    }

    @PutMapping("/rescind/{id}")
    public String rescindContract(@PathVariable String id) {
        return "entity";
    }

}
