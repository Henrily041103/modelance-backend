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
        String url = "https://modelance-backend-rfh7esctoa-uc.a.run.app/";
        String path = "payos/webhook";
        try {
            System.out.println(payOS.confirmWebhook(url + path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
