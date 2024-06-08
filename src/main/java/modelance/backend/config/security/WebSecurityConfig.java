package modelance.backend.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import modelance.backend.service.account.AccountDetailService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private RSAKey rsaKey;

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
    AuthenticationManager accountAuthenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
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
    SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // settings
                .csrf((csrf) -> csrf.disable())
                .sessionManagement(
                        (sessionManagement) -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(Customizer.withDefaults()))
                // account controller
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.POST, "/account/login").permitAll()
                        .requestMatchers("/account/register").permitAll()
                        .requestMatchers("/account/model/**").authenticated()
                        .requestMatchers("/account/employer/**").authenticated()
                        .requestMatchers("/account/password/change").authenticated()
                        .requestMatchers("/account/avatar").authenticated())
                // profile controller
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/profile/details/**").authenticated()
                        .requestMatchers("/profile/edit").authenticated())
                // model's view job controller
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/job/all").authenticated()
                        .requestMatchers("/job/details/**").authenticated())
                // employer job management controller
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/jobmanager/create").authenticated()
                        .requestMatchers("/jobmanager/**").authenticated()
                        .requestMatchers("/jobmanager/details/**").authenticated()
                        .requestMatchers("/jobmanager/finish/**").authenticated()
                        .requestMatchers("/jobmanager/cancel/**").authenticated()
                        .requestMatchers("/jobmanager/close/**").authenticated())
                // logout
                .logout((logout) -> logout
                        .logoutUrl("/account/logout")
                        .logoutSuccessUrl("/"));

        return http.build();
    }

}
