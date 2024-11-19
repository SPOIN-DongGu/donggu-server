package com.donggu.server.global.filter;

import com.donggu.server.domain.auth.handler.DefaultLoginAuthenticationFailureHandler;
import com.donggu.server.domain.auth.handler.DefaultLoginAuthenticationSuccessHandler;
import com.donggu.server.global.exception.CustomException;
import com.donggu.server.global.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class DefaultLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public DefaultLoginAuthenticationFilter(AuthenticationManager authenticationManager,
                                            DefaultLoginAuthenticationSuccessHandler successHandler,
                                            DefaultLoginAuthenticationFailureHandler failureHandler) {
        super(authenticationManager);
        this.setAuthenticationSuccessHandler(successHandler);
        this.setAuthenticationFailureHandler(failureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        if (!"application/json".equals(request.getContentType()) && !request.getMethod().equals("POST")) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        try {
            Map<String, String> authRequest = objectMapper.readValue(request.getInputStream(), Map.class);
            String username = authRequest.get("username");
            String password = authRequest.get("password");

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            return this.getAuthenticationManager().authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
