package com.donggu.server.domain.user.service;

import com.donggu.server.domain.user.domain.Role;
import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.repository.UserRepository;
import com.donggu.server.global.exception.CustomException;
import com.donggu.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public void changeUserRoleToAdmin(Long adminId, Long userId) {
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        if (admin.getRole()!= Role.ROLE_ADMIN) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        user.updateRole(Role.ROLE_ADMIN);
    }
}
