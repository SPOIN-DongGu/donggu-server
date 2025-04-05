package com.donggu.server.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.donggu.server.domain.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
	private final UserService userService;

	@PostMapping("/token")
	@Operation(summary = "[전체] 소셜 로그인 후 JWT 발급", description = "temp 토큰으로 JWT 발급")
	public ResponseEntity<?> getToken(@RequestParam("tempToken") String tempToken, HttpServletResponse response) {
		userService.getToken(tempToken, response);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/token/refresh")
	@Operation(summary = "[전체] 새 access 발급", description = "refresh 토큰으로 새 access 토큰 발급")
	public ResponseEntity<?> reissueToken(HttpServletRequest request, HttpServletResponse response) {
		userService.reissueToken(request, response);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/logout")
	@Operation(summary = "[유저] 로그아웃", description = "더이상 refresh 토큰으로 새로운 토큰을 발급받지 못함")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		userService.logout(request);
		return ResponseEntity.ok().build();
	}
}
