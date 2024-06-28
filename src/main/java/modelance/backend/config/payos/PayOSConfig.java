package modelance.backend.config.payos;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lib.payos.PayOS;

@Configuration
public class PayOSConfig {
    private final ObjectMapper objectMapper;

    public PayOSConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    PaymentAccount paymentAccount() throws IOException {
        Resource resource = new ClassPathResource("paymentAccount.json");
        InputStream serviceAccount = resource.getInputStream();
        PaymentAccount paymentAccount = objectMapper.readValue(serviceAccount, PaymentAccount.class);
        return paymentAccount;
    }

    @Bean
    PayOS getPayOS(PaymentAccount paymentAccount) {
        PayOS payOS = new PayOS(paymentAccount.getClientId(), paymentAccount.getApiKey(),
                paymentAccount.getChecksumKey());

        return payOS;
    }
}
