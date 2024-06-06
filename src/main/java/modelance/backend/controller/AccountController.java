package modelance.backend.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import modelance.backend.config.security.TokenGenerator;

import modelance.backend.model.account.AccountModel;
import modelance.backend.model.account.EmployerModel;
import modelance.backend.model.account.ModelModel;
import modelance.backend.service.account.AccountService;
import modelance.backend.service.account.NoAccountExistsException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final TokenGenerator tokenGenerator;

    public AccountController(AccountService accountService, TokenGenerator tokenGenerator) {
        this.accountService = accountService;
        this.tokenGenerator = tokenGenerator;
    }

    static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    static class LoginResponse {
        private AccountModel account;
        private String jwtToken;
        private String statusMessage;

        public LoginResponse() {
            statusMessage = "Error!";
        }

        public void setAccount(AccountModel account) {
            this.account = account;
        }

        public void setJwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
        }

        public void setStatusMessage(String statusMessage) {
            this.statusMessage = statusMessage;
        }

        public AccountModel getAccount() {
            return account;
        }

        public String getJwtToken() {
            return jwtToken;
        }

        public String getStatusMessage() {
            return statusMessage;
        }

    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest requestBody) {
        LoginResponse response = new LoginResponse();
        try {
            AccountModel account = accountService.login(requestBody.getUsername(), requestBody.getPassword());
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    account.getUsername(),
                    account.getPassword(),
                    accountService.getAuthorities(account));
            String token = tokenGenerator.generateToken(authentication);
            String message = "Success";

            response.setAccount(account);
            response.setJwtToken(token);
            response.setStatusMessage(message);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (NoAccountExistsException e1) {
            // default state, do nothing
        }

        return response;
    }

    @GetMapping("/model/{id}")
    public ModelModel getModel(@PathVariable String id) {
        ModelModel modelAccount = null;
        try {
            modelAccount = accountService.loadModelModel(id);
        } catch (InterruptedException | ExecutionException | NoAccountExistsException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return modelAccount;
    }

    @GetMapping("/employer/{id}")
    public EmployerModel getEmployer(@PathVariable String id) {
        EmployerModel employerAccount = null;
        try {
            employerAccount = accountService.loadEmployerModel(id);
        } catch (InterruptedException | ExecutionException | NoAccountExistsException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return employerAccount;
    }

}
