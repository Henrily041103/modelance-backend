package modelance.backend.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
// import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lib.payos.PayOS;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import modelance.backend.config.security.Jwks;
import modelance.backend.service.account.AccountDetailService;

class PaymentAccount {
    private String clientId;
    private String apiKey;
    private String checksumKey;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getChecksumKey() {
        return checksumKey;
    }

    public void setChecksumKey(String checksumKey) {
        this.checksumKey = checksumKey;
    }

}

@Configuration
@EnableWebSecurity
public class ApplicationConfig {
    private RSAKey rsaKey;

    @Bean
    PayOS payOS() throws StreamReadException, DatabindException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Resource resource = new ClassPathResource("paymentAccount.json");
        InputStream paymentAccount = resource.getInputStream();
        PaymentAccount account = mapper.readValue(paymentAccount, PaymentAccount.class);
        return new PayOS(account.getClientId(), account.getApiKey(), account.getChecksumKey());
    }

    @Bean
    JWKSource<SecurityContext> jwkSource() {
        rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwks) {
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    JwtDecoder jwtDecoder() throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    @Bean
    UserDetailsService users() {
        return new AccountDetailService();
    }

    @Bean
    AuthenticationManager accountAuthenticationManager(UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        final JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("scope"); // defaults to "scope" or "scp"
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_"); // defaults to "SCOPE_"

        final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT"));
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // settings
                .csrf((csrf) -> csrf.disable())
                .cors((cors) -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(
                        (sessionManagement) -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(Customizer.withDefaults()))
                // account controller
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/account/login").permitAll()
                        .requestMatchers("/account/register").permitAll()
                        .requestMatchers("/account/**").authenticated())
                // model controller
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/model/**").hasAuthority("ROLE_MODEL"))
                // employer controller
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/employer/**").hasAuthority("ROLE_EMPLOYER"))
                // admin controller
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN"))
                // job controller
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/job").authenticated()
                        .requestMatchers("/job/**").authenticated())
                // contract controller
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/contract/**").authenticated())
                // wallet controller
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/wallet").authenticated()
                        .requestMatchers("/wallet/**").authenticated())
                // logout
                .logout((logout) -> logout
                        .logoutUrl("/account/logout")
                        .logoutSuccessUrl("/"))
                // swaggerUI
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll())
                // payos
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/payos/**").permitAll());
        return http.build();
    }
}
