package modelance.backend.config.security;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import modelance.backend.model.account.AccountModel;
import modelance.backend.service.account.AccountService;
import modelance.backend.service.account.NoAccountExistsException;

@Service
public class AccountDetailService implements UserDetailsService {
    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            AccountModel account = accountService.loadUserByUsername(username);
            AccountPrincipal accountPrincipal = new AccountPrincipal(account);
            return accountPrincipal;
        } catch (InterruptedException | ExecutionException | NoAccountExistsException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
