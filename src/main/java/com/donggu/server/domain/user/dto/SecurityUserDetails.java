package com.donggu.server.domain.user.dto;

import com.donggu.server.domain.user.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class SecurityUserDetails implements UserDetails {

    private final User securityUser;

    public SecurityUserDetails(User user) {
        this.securityUser = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + securityUser.getRole().name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return securityUser.getPassword();
    }

    @Override
    public String getUsername() {
        return securityUser.getUsername();
    }
}
