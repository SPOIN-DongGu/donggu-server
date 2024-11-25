package com.donggu.server.domain.user.controller;

import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.service.AdminService;
import com.donggu.server.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 테스트를 위한 임시 컨트롤러
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;

    // 테스트를 위한 임시 API
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user")
    @Operation(summary = "user 목록 조회", description = "테스트를 위한 회원 목록 조회")
    public ResponseEntity<List<User>> getUserList() {
        return ResponseEntity.ok(userService.getUserList());
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{userId}")
    @Operation(summary = "권한 변경(USER->ADMIN)", description = "관리자 권한 부여")
    public ResponseEntity<Void> changeUserRoleToAdmin(@PathVariable Long userId) {
        adminService.changeUserRoleToAdmin(userId);
        return ResponseEntity.ok().build();
    }
}
