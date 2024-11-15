package com.donggu.server.domain.user.service;

import com.donggu.server.domain.user.domain.Role;
import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public void changeUserRoleToAdmin(Long adminId, Long userId) {
        User admin = userRepository.findById(adminId).orElseThrow();

        if (admin.getRole()!= Role.ROLE_ADMIN) {
            throw new IllegalArgumentException("관리자가 아닙니다.");
        }

        User user = userRepository.findById(userId).orElseThrow();
        user.updateRole(Role.ROLE_ADMIN);
    }
}
