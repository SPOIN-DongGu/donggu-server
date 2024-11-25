package com.donggu.server.domain.auth.provider;

import com.donggu.server.domain.auth.dto.AuthUserDto;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OAuth2AttributeProvider {

    public AuthUserDto convertUserAttribute(String registrationId, OAuth2User oAuth2User) {

        if (registrationId.equals("kakao")) {
            return kakaoUserAttribute(oAuth2User);
        } else if (registrationId.equals("google")) {
            return googleUserAttribute(oAuth2User);
        }

        return null;
    }

    private AuthUserDto kakaoUserAttribute(OAuth2User oAuth2User) {
        Map<String, Object> attribute = oAuth2User.getAttributes();
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return AuthUserDto.of(profile.get("nickname").toString(), kakaoAccount.get("email").toString());
    }

    private AuthUserDto googleUserAttribute(OAuth2User oAuth2User) {
        Map<String, Object> attribute = oAuth2User.getAttributes();

        return AuthUserDto.of(attribute.get("name").toString(), attribute.get("email").toString());
    }
}
