package com.seventeamproject.common.security.principal;

import com.seventeamproject.api.admin.entity.Admin;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class PrincipalUser implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;

    public PrincipalUser(Admin admin){
        this.id = admin.getId();
        this.email = admin.getEmail();
        this.password = admin.getPassword();
        this.enabled = admin.isEnabled();
        this.authorities = List.of(
                new SimpleGrantedAuthority(admin.getRole().getAuthority())
        );
    }

    @Override public String getUsername(){ return email; }
    @Override public String getPassword(){ return password; }
    @Override public boolean isEnabled(){ return enabled; }
    @Override public boolean isAccountNonExpired(){ return true; }
    @Override public boolean isAccountNonLocked(){ return true; }
    @Override public boolean isCredentialsNonExpired(){ return true; }
}