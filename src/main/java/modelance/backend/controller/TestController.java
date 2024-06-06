package modelance.backend.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// import modelance.backend.config.security.TokenGenerator;


@RestController
@Controller
public class TestController {

    // private final TokenGenerator tokenService;

    // public TestController(TokenGenerator tokenService) {
    //     this.tokenService = tokenService;
    // }
    
    @GetMapping("/")
    public String defaultString() {
        return "hello";
    }

    @GetMapping("/protected")
    public String protectedString(Principal principal) {
        return "hi " + principal.getName();
    }

    @PostMapping("/token")
    public Map<String, Object> token(JwtAuthenticationToken principal) {
        Collection<String> authorities = principal.getAuthorities()
          .stream()
          .map(GrantedAuthority::getAuthority)
          .collect(Collectors.toList());
        
        Map<String,Object> info = new HashMap<>();
        info.put("name", principal.getName());
        info.put("authorities", authorities);
        info.put("tokenAttributes", principal.getTokenAttributes());

        return info;
    }
    
}
