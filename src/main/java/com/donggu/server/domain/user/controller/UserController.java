package com.donggu.server.domain.user.controller;

import com.donggu.server.domain.user.dto.UserJoinRequestDto;
import com.donggu.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Void> joinUser(@RequestBody UserJoinRequestDto dto) {
        userService.joinUser(dto);
        return ResponseEntity.ok().build();
    }
}
