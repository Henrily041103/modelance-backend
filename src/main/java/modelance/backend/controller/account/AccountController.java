package modelance.backend.controller.account;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import modelance.backend.config.security.TokenGenerator;
import modelance.backend.firebasedto.account.AccountDTO;
import modelance.backend.model.AccountModel;
import modelance.backend.service.account.AccountService;
import modelance.backend.service.account.NoAccountExistsException;
import modelance.backend.service.account.QueryMismatchException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final TokenGenerator tokenGenerator;
    private final ObjectMapper objectMapper;

    public AccountController(AccountService accountService, TokenGenerator tokenGenerator, ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.tokenGenerator = tokenGenerator;
        this.objectMapper = objectMapper;
    }

    @GetMapping("")
    public AccountDTO getProfile(Authentication authentication) {
        AccountDTO employerAccount = null;
        try {
            employerAccount = accountService.getCurrentAccount(authentication);
        } catch (InterruptedException | ExecutionException | NoAccountExistsException e) {
            e.printStackTrace();
        }
        return employerAccount;
    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest requestBody) {
        LoginResponse response = new LoginResponse();
        try {
            AccountDTO accountDTO = accountService.login(requestBody.getUsername(), requestBody.getPassword());
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    accountDTO.getId(),
                    accountDTO.getPassword(),
                    accountService.getAuthorities(accountDTO));
            String token = tokenGenerator.generateToken(authentication);
            String message = "Success";

            AccountModel account = new AccountModel(accountDTO);

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

    @PostMapping("register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        RegisterResponse response = new RegisterResponse();
        response.setMessage("failed");

        try {
            AccountDTO accountDTO = accountService.register(request.getUsername(), request.getPassword(),
                    request.getEmail(), request.getRole());

            AccountModel account = new AccountModel(accountDTO);
            response.setAccount(account);
            if (account != null)
                response.setMessage("success");
        } catch (InterruptedException | ExecutionException | NoAccountExistsException e) {
            e.printStackTrace();
        }

        return response;
    }

    @GetMapping("model/{id}")
    public AccountDTO getModel(@PathVariable String id) {
        AccountDTO modelAccount = null;
        try {
            modelAccount = accountService.getAccountByRoleId(id, "role_model");
        } catch (InterruptedException | ExecutionException | NoAccountExistsException e) {
            e.printStackTrace();
        }
        return modelAccount;
    }

    @GetMapping("model")
    public List<AccountDTO> getModelList(@RequestParam(required = false, defaultValue = "50") Integer limit) {
        List<AccountDTO> modelList = null;
        try {
            modelList = accountService.getAccountsByRole("model", limit);
        } catch (InterruptedException | ExecutionException | QueryMismatchException e) {
            e.printStackTrace();
        }
        return modelList;
    }

    @GetMapping("employer/{id}")
    public AccountDTO getEmployer(@PathVariable String id) {
        AccountDTO employerAccount = null;
        try {
            employerAccount = accountService.getAccountByRoleId(id, "role_employer");
        } catch (InterruptedException | ExecutionException | NoAccountExistsException e) {
            e.printStackTrace();
        }
        return employerAccount;
    }

    @GetMapping("employer")
    public List<AccountDTO> getEmployerList(@RequestParam(required = false, defaultValue = "50") Integer limit) {
        List<AccountDTO> employerList = null;
        try {
            employerList = accountService.getAccountsByRole("employer", limit);
        } catch (InterruptedException | ExecutionException | QueryMismatchException e) {
            e.printStackTrace();
        }
        return employerList;
    }

    @PostMapping("password/change")
    public ChangePasswordResponse changePassword(
            @RequestBody ChangePasswordRequest request,
            Authentication authentication) {
        ChangePasswordResponse response = new ChangePasswordResponse();
        response.setResult(false);
        try {
            response.setResult(
                    accountService.changePassword(request.getOldPassword(), request.getNewPassword(), authentication));
        } catch (InterruptedException | ExecutionException | NoAccountExistsException e) {
            e.printStackTrace();
        }

        return response;
    }

    @PostMapping("change")
    public ChangeAccountDataResponse changeAccountData(
            @RequestBody ChangeAccountDataRequest updates,
            Authentication authentication) {
        ChangeAccountDataResponse response = new ChangeAccountDataResponse();
        response.setResult(false);

        try {
            Map<String, Object> updatesMap = objectMapper.convertValue(updates,
                    new TypeReference<Map<String, Object>>() {
                    });
            response.setResult(accountService.changeAccountData(updatesMap, authentication));
        } catch (InterruptedException | ExecutionException | NoAccountExistsException e) {
            e.printStackTrace();
        }

        return response;
    }

    @PostMapping(path = "avatar", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ChangeAvatarResponse changeAvatar(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {
        ChangeAvatarResponse response = new ChangeAvatarResponse();
        response.setMessage("failed");
        try {
            String url = accountService.uploadAvatar(file, authentication);
            if (url != null && url.trim() != "") {
                response.setMessage("success");
                response.setUrl(url);
            }
        } catch (IOException | NoAccountExistsException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return response;
    }

}
