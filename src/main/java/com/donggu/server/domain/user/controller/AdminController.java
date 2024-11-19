package com.donggu.server.domain.user.controller;

import com.donggu.server.domain.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

// 테스트를 위한 임시 컨트롤러
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/admin/changeRole/{userId}")
    public ResponseEntity<Void> changeUserRoleToAdmin(@PathVariable Long userId) {
        adminService.changeUserRoleToAdmin(userId);
        return ResponseEntity.ok().build();
    }
}
