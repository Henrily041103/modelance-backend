package modelance.backend.controller.wallet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import modelance.backend.firebasedto.wallet.CheckoutResponseDTO;
import modelance.backend.firebasedto.wallet.OrderTransactionDTO;
import modelance.backend.firebasedto.wallet.TransactionDTO;
import modelance.backend.firebasedto.wallet.WalletDTO;
import modelance.backend.service.account.NoAccountExistsException;
import modelance.backend.service.wallet.WalletService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping(path = "/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("")
    public WalletDTO getWallet(Authentication authentication) {
        WalletDTO result = null;

        try {
            result = walletService.getOwnWallet(authentication);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } catch (NoAccountExistsException e) {
            e.printStackTrace();
        }

        return result;
    }

    @GetMapping("history")
    public List<TransactionDTO> getTransactionHistory(Authentication authentication) {
        List<TransactionDTO> result = null;

        try {
            result = walletService.getTransactions(authentication);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } catch (NoAccountExistsException e) {
            e.printStackTrace();
        }

        return result;
    }

    @PostMapping("topup")
    public CheckoutResponseDTO createLink(@RequestBody int amount, Authentication authentication) {
        CheckoutResponseDTO response = null;

        try {
            response = walletService.createBankTransaction(amount, authentication);
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }
        
        return response;
    }

    @GetMapping("topup/{id}")
    public OrderTransactionDTO getTransactionInfo(@PathVariable String id) {
        OrderTransactionDTO response = null;

        try {
            response = walletService.getBankTransaction(id);
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }
        
        return response;
    }
    
    
}
