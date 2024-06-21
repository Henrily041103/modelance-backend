package modelance.backend.controller.job;

import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import modelance.backend.firebasedto.work.ContractDTO;
import modelance.backend.service.job.ContractService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/contract")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping("{id}")
    public ContractDTO getContract(@PathVariable String id) {
        ContractDTO contract = null;

        try {
            contract = contractService.getContract(id);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return contract;
    }

    @PutMapping("{id}/edit")
    public ContractDTO editContract(@PathVariable String id, @RequestBody ContractEditRequest request) {
        ContractDTO contract = null;

        try {
            contract = contractService.changeContract(
                    id, request.getEmployerTerms(), request.getPayment(),
                    request.getStartDate(), request.getEndDate());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return contract;
    }

    @PutMapping("{id}/status")
    public ContractDTO editStatus(@PathVariable String id, @RequestBody ContractStatusEditRequest request) {
        ContractDTO contract = null;

        try {
            contract = contractService.changeContractStatus(request.getStatusName(), id);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return contract;
    }
}
