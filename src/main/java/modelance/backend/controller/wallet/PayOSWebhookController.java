package modelance.backend.controller.wallet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import modelance.backend.service.wallet.WalletService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "/payos")
public class PayOSWebhookController {
    
    private final WalletService walletService;

    public PayOSWebhookController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/webhook")
    public Map<String, Boolean> postWebhook(@RequestBody JsonNode data) {
        Map<String, Boolean> entity = new HashMap<>();

        try {
            boolean result = walletService.receiveBankTransaction(data);
            entity.put("success", result);
        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            entity.put("success", false);
        }
        
        return entity;
    }

    @GetMapping("/webhook")
    public Map<String, Boolean> getWebhook(@RequestBody JsonNode data) {
        Map<String, Boolean> entity = new HashMap<>();

        try {
            boolean result = walletService.receiveBankTransaction(data);
            entity.put("success", result);
        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        
        return entity;
    }
    
}
