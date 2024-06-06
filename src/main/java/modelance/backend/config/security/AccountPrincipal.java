package modelance.backend.config.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import modelance.backend.model.account.AccountModel;

class AccountAuthority implements GrantedAuthority {

    private String authority;

    public AccountAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}

public class AccountPrincipal implements UserDetails {

    private AccountModel account;

    public AccountPrincipal(AccountModel account) {
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
        return account.getUsername();
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

}
