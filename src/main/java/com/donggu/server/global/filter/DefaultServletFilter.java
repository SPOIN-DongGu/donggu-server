package com.donggu.server.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class DefaultServletFilter extends OncePerRequestFilter {
    private final Logger LOGGER = LoggerFactory.getLogger(DefaultServletFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String requestAddr = getOriginRemoteAddr(request);

        LOGGER.info("[LOGGER] RequestURI: {}, RequestHost: {}", requestURI, requestAddr);
        filterChain.doFilter(request, response);
    }

    private static String getOriginRemoteAddr(HttpServletRequest request) {
        String originAddr = request.getHeader("X-Real-IP");

        if (originAddr == null || originAddr.isBlank()) {
            originAddr = request.getRemoteAddr();
        }

        return originAddr;
    }
}