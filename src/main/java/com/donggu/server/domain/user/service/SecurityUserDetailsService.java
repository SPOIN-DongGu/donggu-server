package com.donggu.server.domain.user.service;

import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.dto.SecurityUserDetails;
import com.donggu.server.global.exception.CustomException;
import com.donggu.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public UserDetails loadUserByUsername(String username) {
        User user = userService.findByUsername(username);

        if (user == null) {
            System.out.println(username);
            throw new CustomException(ErrorCode.NOT_FOUND);
        }

        return new SecurityUserDetails(user);
    }
}
