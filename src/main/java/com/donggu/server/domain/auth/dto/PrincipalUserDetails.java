package com.donggu.server.domain.auth.dto;

import com.donggu.server.domain.user.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
public class PrincipalUserDetails implements UserDetails, OAuth2User {

    private final User principalUser;
    private final Map<String, Object> attribute;

    public PrincipalUserDetails(User user) {
        this(user, null);
    }

    public PrincipalUserDetails(User user, Map<String, Object> attribute) {
        this.principalUser = user;
        this.attribute = attribute;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attribute;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(principalUser.getRole().name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return principalUser.getEmail();
    }

    @Override
    public String getName() {
        return String.valueOf(principalUser.getId());
    }
}
