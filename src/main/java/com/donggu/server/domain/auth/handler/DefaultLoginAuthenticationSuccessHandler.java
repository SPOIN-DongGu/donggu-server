package com.donggu.server.domain.auth.handler;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

import com.donggu.server.domain.auth.dto.PrincipalUserDetails;
import com.donggu.server.domain.user.domain.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${spring.data.redis.expire-minutes}")
    private Long EXPIRE;
    @Value("${front-url}")
    private String FRONT_URL;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.info("[Login Success] Login successful and ...");

        PrincipalUserDetails userDetails = (PrincipalUserDetails) authentication.getPrincipal();

        User user = userDetails.getPrincipalUser();

        String tempToken = UUID.randomUUID().toString();

        String redisKey = "tempToken:"+tempToken;
        redisTemplate.opsForValue().set(redisKey, user.getId().toString(), Duration.ofMinutes(EXPIRE));

        log.info("[TempToken] TempToken generated and stored in Redis");

        String redirectUrl = FRONT_URL + "/oauth2/redirect?tempToken=" + tempToken;
        response.sendRedirect(redirectUrl);
    }
}
