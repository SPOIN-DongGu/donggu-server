package com.donggu.server.domain.auth.handler;

import com.donggu.server.domain.auth.provider.AuthTokenProvider;
import com.donggu.server.domain.auth.token.AccessToken;
import com.donggu.server.domain.auth.token.RefreshToken;
import com.donggu.server.domain.user.dto.SecurityUserDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthTokenProvider authTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();

        AccessToken accessToken = authTokenProvider.createAccessToken(userDetails.getSecurityUser());
        RefreshToken refreshToken = authTokenProvider.createRefreshToken(userDetails.getSecurityUser());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Authorization", "Bearer " + accessToken.token());
        response.addCookie(createCookie(refreshToken));
    }

    private Cookie createCookie(RefreshToken refreshToken) {
        Cookie cookie = new Cookie("refresh", refreshToken.token());
        cookie.setMaxAge((int) authTokenProvider.getRefreshExpirationTime());
        cookie.setHttpOnly(true);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        // 위 두 설정의 경우 https 통신에서 필요하다는데 일단 보류

        return cookie;
    }
}
