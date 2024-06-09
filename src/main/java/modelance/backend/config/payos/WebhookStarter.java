package modelance.backend.config.payos;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.lib.payos.PayOS;

@Component
public class WebhookStarter {
    private final PayOS payOS;

    public WebhookStarter(PayOS payOS) {
        this.payOS = payOS;
    }

    @EventListener
    public void registerWebhook(ApplicationReadyEvent event) {
        String url = "http://localhost:8080";
        String path = "/wallet/transaction/webhook";
        try {
            payOS.confirmWebhook(url + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
