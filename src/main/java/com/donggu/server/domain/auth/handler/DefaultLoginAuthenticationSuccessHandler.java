package com.donggu.server.domain.auth.handler;

import com.donggu.server.domain.auth.dto.PrincipalUserDetails;
import com.donggu.server.domain.auth.provider.AuthTokenProvider;
import com.donggu.server.domain.auth.token.AccessToken;
import com.donggu.server.domain.auth.token.RefreshToken;
import com.donggu.server.domain.user.domain.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthTokenProvider authTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        log.info("[Login Success] login success handler ...");

        PrincipalUserDetails userDetails = (PrincipalUserDetails) authentication.getPrincipal();

        User user = userDetails.getPrincipalUser();
        AccessToken accessToken = authTokenProvider.generateAccessToken(user);
        RefreshToken refreshToken = authTokenProvider.generateRefreshToken(user);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Authorization", "Bearer " + accessToken.token());
        //response.sendRedirect("http://localhost:3000/");
        response.addCookie(createCookie(refreshToken));
    }

    private Cookie createCookie(RefreshToken refreshToken) {
        Cookie cookie = new Cookie("refresh", refreshToken.token());
        cookie.setMaxAge((int) authTokenProvider.getRefreshExpirationTime());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");

        return cookie;
    }
}
