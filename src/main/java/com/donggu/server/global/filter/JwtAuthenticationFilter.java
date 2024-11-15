package com.donggu.server.global.filter;

import com.donggu.server.domain.auth.provider.AuthTokenProvider;
import com.donggu.server.domain.user.service.SecurityUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthTokenProvider authTokenProvider;
    private final SecurityUserDetailsService securityUserDetailsService;
    private static final String BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // 접근을 어떻게 허용하지ㅜㅜ..
        String path = request.getRequestURI();
        if (path.endsWith("/pickup/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 1. 토큰 검증
        // 2. 유저 확인
        // 3. 인증 정보 저장

        String username = resolveToken(request);

        if (username != null) {
            UserDetails userDetails = securityUserDetailsService.loadUserByUsername(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            throw new IllegalArgumentException("잘못된 접근");
        }

        String token = authHeader.substring(BEARER.length());
        if (authTokenProvider.validateToken(token)) {
            return authTokenProvider.getSubject(token);
        }

        throw new IllegalArgumentException("잘못된 토큰");
    }
}
