package com.donggu.server.domain.user.service;

import com.donggu.server.domain.auth.dto.AuthUserDto;
import com.donggu.server.domain.auth.provider.AuthTokenProvider;
import com.donggu.server.domain.auth.token.AccessToken;
import com.donggu.server.domain.auth.token.RefreshToken;
import com.donggu.server.domain.user.domain.Role;
import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.repository.UserRepository;
import com.donggu.server.global.exception.CustomException;
import com.donggu.server.global.exception.ErrorCode;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    @Value("${jwt.refresh-expiration-time}")
    private Long REFRESH_EXPIRATION_TIME;

    private final UserRepository userRepository;
    private final AuthTokenProvider authTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public User registerOrLogin(AuthUserDto authUserDto) {
        log.info("[User] Trying to login ...");

        return userRepository.findByEmail(authUserDto.email())
                .orElseGet(() -> {
                    User user = User.builder()
                            .email(authUserDto.email())
                            .role(Role.ROLE_USER)
                            .build();
                    return userRepository.save(user);
                });
    }

    @Transactional
    public void getToken(String tempToken, HttpServletResponse response) {
        log.info("[Issue Token] Starting to issue token ...");

        String redisKey = "tempToken:" + tempToken;

        String userIdStr = (String) redisTemplate.opsForValue().get(redisKey);
        if (userIdStr == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        Long userId = Long.parseLong(userIdStr);
        User user = findById(userId);

        AccessToken accessToken = authTokenProvider.generateAccessToken(user);
        RefreshToken refreshToken = authTokenProvider.generateRefreshToken(user);

        redisTemplate.delete(redisKey);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken.token())
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(REFRESH_EXPIRATION_TIME)
            .sameSite("Strict")
            .build();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Authorization", "Bearer " + accessToken.token());
        response.setHeader("Set-Cookie", refreshTokenCookie.toString());

        log.info("[Issue Token] Succeeded to token issuance");
    }

    @Transactional
    public void reissueToken(HttpServletRequest request,  HttpServletResponse response) {
        // 1. 토큰 추출 후 검증
        // 2. 사용자 확인
        // 3. 사용자의 토큰와 요청된 토큰 일치 확인
        // 4. 코튼 재발급
        log.info("[Reissue Token] Starting to reissue token ...");

        String refresh = getRefreshTokenFromCookies(request);

        if (refresh != null
            && authTokenProvider.validateToken(refresh)
            && Boolean.TRUE.equals(redisTemplate.hasKey("blacklist:" + refresh))) {

            String username = authTokenProvider.getSubject(refresh);
            Role role = authTokenProvider.getRole(refresh);

            AccessToken accessToken = authTokenProvider.generateAccessToken(username, role);

            response.setHeader("Authorization", "Bearer " + accessToken.token());
        } else {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    @Transactional
    public void logout(HttpServletRequest request) {
        String refresh = getRefreshTokenFromCookies(request);
        String redisKey = "blacklist:" + refresh;

        if (refresh != null && authTokenProvider.validateToken(refresh)) {
            redisTemplate.opsForValue().set(redisKey, "logout", Duration.ofMinutes(REFRESH_EXPIRATION_TIME));
        } else {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    private String getRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }
}
