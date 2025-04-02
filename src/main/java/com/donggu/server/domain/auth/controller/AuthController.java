package com.donggu.server.domain.auth.controller;

import com.donggu.server.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/refresh")
    @Operation(summary = "[전체] 새 access 발급", description = "refresh 토큰으로 새 access 토큰 발급")
    public ResponseEntity<?> reissueToken(HttpServletRequest request, HttpServletResponse response) {
        log.info("[Reissue] Refresh token");
        authService.reissueToken(request, response);
        return ResponseEntity.ok().build();
    }
}
