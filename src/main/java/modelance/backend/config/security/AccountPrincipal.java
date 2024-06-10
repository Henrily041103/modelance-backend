package modelance.backend.config.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import modelance.backend.firebasedto.account.AccountDTO;

import java.util.List;
public class AccountPrincipal implements UserDetails {

    private AccountDTO account;

    public AccountPrincipal(AccountDTO account) {
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        auths.add(new AccountAuthority("ROLE_" + account.getRole().getRoleName().toUpperCase()));
        return auths;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return account.getStatus().getStatusName().toLowerCase() == "active";
    }

    @Override
    public boolean isAccountNonLocked() {
        return account.getStatus().getStatusName().toLowerCase() == "active";
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return account.getStatus().getStatusName().toLowerCase() == "active";
    }

    @Override
    public boolean isEnabled() {
        return account.getStatus().getStatusName().toLowerCase() == "active";
    }

    public AccountDTO getAccount() {
        return account;
    }

}
