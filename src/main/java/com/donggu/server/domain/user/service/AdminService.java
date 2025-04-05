package com.donggu.server.domain.user.service;

import com.donggu.server.domain.user.domain.Role;
import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.repository.UserRepository;
import com.donggu.server.global.exception.CustomException;
import com.donggu.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final UserRepository userRepository;

    @Transactional
    public void changeUserRoleToAdmin(Long userId) {
        log.info("[User] Get all user list");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        user.updateRole(Role.ROLE_ADMIN);
        userRepository.save(user);
    }
}
