package com.donggu.server.domain.user.controller;

import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.dto.UserJoinRequestDto;
import com.donggu.server.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    @Operation(summary = "회원가입", description = "username과 password로 회원가입을 진행합니다")
    public ResponseEntity<Void> joinUser(@RequestBody UserJoinRequestDto dto) {
        userService.joinUser(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    @Operation(summary = "user 목록 조회", description = "테스트를 위한 회원 목록 조회")
    public ResponseEntity<List<User>> getUserList() {
        return ResponseEntity.ok(userService.getUserList());
    }
}
