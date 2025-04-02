package com.donggu.server.domain.user.service;

import com.donggu.server.domain.auth.dto.AuthUserDto;
import com.donggu.server.domain.user.domain.Role;
import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.repository.UserRepository;
import com.donggu.server.global.exception.CustomException;
import com.donggu.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User registerOrLogin(AuthUserDto authUserDto) {
        log.info("[User] Trying to login ...");

        return userRepository.findByEmail(authUserDto.email())
                .orElseGet(() -> {
                    User user = User.builder()
                            .email(authUserDto.email())
                            .role(Role.ROLE_USER)
                            .build();
                    return userRepository.save(user);
                });
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
