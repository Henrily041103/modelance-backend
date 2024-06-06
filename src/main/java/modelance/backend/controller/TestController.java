package modelance.backend.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import modelance.backend.config.security.TokenGenerator;


@RestController
@Controller
public class TestController {

    private final TokenGenerator tokenService;

    public TestController(TokenGenerator tokenService) {
        this.tokenService = tokenService;
    }
    
    @GetMapping("/")
    public String defaultString() {
        return "hello";
    }

    @GetMapping("/protected")
    public String protectedString(Principal principal) {
        return "hi " + principal.getName();
    }

    @PostMapping("/token")
    public String token(Authentication authentication) {
        String token = tokenService.generateToken(authentication);
        return token;
    }
    
}
