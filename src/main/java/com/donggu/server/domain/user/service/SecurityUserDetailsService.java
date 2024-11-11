package com.donggu.server.domain.user.service;

import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.dto.SecurityUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService {

    private final UserService userService;

    public UserDetails loadUserByUsername(String username) {
        User user = userService.findUserByUsername(username);

        if (user == null) {
            throw new IllegalArgumentException("유저가 존재하지 않음");
        }

        return new SecurityUserDetails(user);
    }
}
