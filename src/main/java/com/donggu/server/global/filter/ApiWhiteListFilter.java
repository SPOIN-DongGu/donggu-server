package com.donggu.server.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiWhiteListFilter extends OncePerRequestFilter {

    private static final List<Pattern> WHITE_LIST_ENDPOINTS = List.of(
            Pattern.compile("^/login.*$"),
            Pattern.compile("^/oauth2/authorization/\\w+$"),
            Pattern.compile("^/admin/user.*$"),
            Pattern.compile("^/admin/pickup.*$"),
            Pattern.compile("^/admin/\\d+/apply.*$"),
            Pattern.compile("^/pickup.*$")
    );

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        boolean isAllowed = WHITE_LIST_ENDPOINTS.stream().anyMatch(pattern -> pattern.matcher(requestUri).matches());

        if (!isAllowed) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Forbidden");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
