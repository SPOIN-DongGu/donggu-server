package com.donggu.server.domain.auth.controller;

import com.donggu.server.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    // 추후 refresh 토큰 추가하면 다시 활성화
    /*@PostMapping("/refresh")
    @Operation(summary = "새 access 발급", description = "refresh 토큰으로 새 access 토큰 발급")
    public ResponseEntity<?> reissueToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.reissueToken(request, response);
        return ResponseEntity.ok().build();
    }*/
}
