package com.donggu.server.global.config;

import com.donggu.server.domain.auth.handler.DefaultLoginAuthenticationFailureHandler;
import com.donggu.server.domain.auth.handler.DefaultLoginAuthenticationSuccessHandler;
import com.donggu.server.domain.auth.provider.AuthTokenProvider;
import com.donggu.server.domain.user.service.SecurityUserDetailsService;
import com.donggu.server.global.filter.DefaultLoginAuthenticationFilter;
import com.donggu.server.global.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final AuthTokenProvider authTokenProvider;
    private final SecurityUserDetailsService securityUserDetailsService;
    private final DefaultLoginAuthenticationSuccessHandler successHandler;
    private final DefaultLoginAuthenticationFailureHandler failureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(
                                "/", "/login", "/user/join", "/auth/refresh", "/user/",
                                "/actuator/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                                ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/pickup/").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(defaultLoginAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(authTokenProvider, securityUserDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DefaultLoginAuthenticationFilter defaultLoginAuthenticationFilter(AuthenticationManager authenticationManager) {
        DefaultLoginAuthenticationFilter filter = new DefaultLoginAuthenticationFilter(
                authenticationManager,
                successHandler,
                failureHandler
        );
        filter.setFilterProcessesUrl("/login");
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
