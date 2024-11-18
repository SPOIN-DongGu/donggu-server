package com.donggu.server.domain.user.service;

import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.dto.SecurityUserDetails;
import com.donggu.server.global.exception.CustomException;
import com.donggu.server.global.exception.ErrorCode;
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
            throw new CustomException(ErrorCode.NOT_FOUND);
        }

        return new SecurityUserDetails(user);
    }
}
