package modelance.backend.controller.wallet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import modelance.backend.firebasedto.wallet.TransactionDTO;
import modelance.backend.firebasedto.wallet.WalletDTO;
import modelance.backend.service.account.NoAccountExistsException;
import modelance.backend.service.wallet.WalletService;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

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
}
