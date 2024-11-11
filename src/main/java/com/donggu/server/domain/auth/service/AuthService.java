package com.donggu.server.domain.auth.service;

import com.donggu.server.domain.auth.provider.AuthTokenProvider;
import com.donggu.server.domain.auth.token.AccessToken;
import com.donggu.server.domain.user.domain.Role;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthTokenProvider authTokenProvider;

    public void reissueToken(HttpServletRequest request,  HttpServletResponse response) throws IOException {
        // 1. 토큰 추출 후 검증
        // 2. 사용자 확인
        // 3. 사용자의 토큰와 요청된 토큰 일치 확인
        // 4. 코튼 재발급

        String refresh = getRefreshTokenFromCookies(request);

        if (refresh != null && authTokenProvider.validateToken(refresh)) {
            String username = authTokenProvider.getSubject(refresh);
            Role role = authTokenProvider.getRole(refresh);

            AccessToken accessToken = authTokenProvider.createAccessToken(username, role);

            response.setHeader("Authorization", "Bearer " + accessToken.token());
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Refresh Token");
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
}
