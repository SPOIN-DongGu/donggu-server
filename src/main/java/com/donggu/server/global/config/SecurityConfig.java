package com.donggu.server.global.config;

import com.donggu.server.domain.auth.handler.DefaultLoginAuthenticationFailureHandler;
import com.donggu.server.domain.auth.handler.DefaultLoginAuthenticationSuccessHandler;
import com.donggu.server.domain.auth.provider.AuthTokenProvider;
import com.donggu.server.domain.auth.service.PrincipalUserDetailsService;
import com.donggu.server.global.filter.DefaultServletFilter;
import com.donggu.server.global.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final AuthTokenProvider authTokenProvider;
    private final PrincipalUserDetailsService principalUserDetailsService;
    private final DefaultLoginAuthenticationSuccessHandler oAuthSuccessHandler;
    private final DefaultLoginAuthenticationFailureHandler oAuthFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new DefaultServletFilter(), UsernamePasswordAuthenticationFilter.class)

                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.GET, "/api/pickup/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/pickup/detail/*").permitAll()
                        .anyRequest().authenticated())

                .logout(LogoutConfigurer::permitAll)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(principalUserDetailsService))
                        .successHandler(oAuthSuccessHandler)
                        .failureHandler(oAuthFailureHandler)) // oauth2

                .addFilterBefore(new JwtAuthenticationFilter(authTokenProvider, principalUserDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080", "http://172.30.1.82:3000", "https://donggu-582cd.web.app"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Accept", "Authorization", "X-Real-IP"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
