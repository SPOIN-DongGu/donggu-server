package com.donggu.server.domain.auth.service;

import com.donggu.server.domain.auth.dto.AuthUserDto;
import com.donggu.server.domain.auth.dto.PrincipalUserDetails;
import com.donggu.server.domain.auth.provider.OAuth2AttributeProvider;
import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PrincipalUserDetailsService extends DefaultOAuth2UserService implements UserDetailsService {

    private final OAuth2AttributeProvider oAuth2AttributeProvider;
    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // userRequest: (로그인 요청 -> 인가 코드 -> 토큰 -> 사용자 정보)의 프로세스에서 최종적으로 반환된 사용자 정보
        // 사용자 정보 추출 -> email로 회원 존재 확인 -> 회원을 등록하거나 업데이트 -> 회원을 OAuth2User로 감싸서 반환

        log.info("[OAuth2 Login] Getting user info from oauth2 server");

        // 사용자 정보 (registrationId + attribute + etc)
        OAuth2User oAuth2User = super.loadUser(userRequest);

        AuthUserDto authUserDto = oAuth2AttributeProvider
                .convertUserAttribute(userRequest.getClientRegistration().getRegistrationId(), oAuth2User);

        User user = userService.registerOrLogin(authUserDto);

        return new PrincipalUserDetails(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);

        return new PrincipalUserDetails(user);
    }
}
