package modelance.backend.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import modelance.backend.model.account.AccountModel;
import modelance.backend.service.account.AccountService;
import modelance.backend.service.account.NoAccountExistsException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    private static class LoginRequestBody {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

    }

    @PostMapping("login")
    public String login(@RequestBody LoginRequestBody requestBody) {
        String message = "Something went wrong!";

        AccountModel account;
        try {
            account = accountService.login(requestBody.getUsername(), requestBody.getPassword());
            message = account.toString();
        } catch (InterruptedException | ExecutionException | NoAccountExistsException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return message;
    }

}
