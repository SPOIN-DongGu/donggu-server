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

@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    @Operation(summary = "[관리자] user 목록 조회", description = "전체 회원 목록을 조회합니다")
    public ResponseEntity<List<User>> getUserList() {
        return ResponseEntity.ok(userService.getUserList());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add/{userId}")
    @Operation(summary = "[관리자] 권한 변경(USER->ADMIN)", description = "유저에게 관리자 권한을 부여합니다")
    public ResponseEntity<Void> changeUserRoleToAdmin(@PathVariable Long userId) {
        adminService.changeUserRoleToAdmin(userId);
        return ResponseEntity.ok().build();
    }
}
