package com.donggu.server.domain.auth.provider;

import com.donggu.server.domain.auth.dto.AuthUserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OAuth2AttributeProviderTest {

    @InjectMocks
    private OAuth2AttributeProvider oAuth2AttributeProvider = new OAuth2AttributeProvider();

    @Test
    void convertUserAttribute_SuccessForKakaoUser() {
        OAuth2User oAuth2User = Mockito.mock(OAuth2User.class);
        Map<String, Object> profile = Map.of("nickname", "testKakao");
        Map<String, Object> kakaoAccount = Map.of(
                "email", "test@kakao.com",
                "profile", profile
        );
        Map<String, Object> attribute = Map.of("kakao_account", kakaoAccount);

        when(oAuth2User.getAttributes()).thenReturn(attribute);

        AuthUserDto dto = oAuth2AttributeProvider.convertUserAttribute("kakao", oAuth2User);

        assertNotNull(dto);
        assertEquals(dto.name(), profile.get("nickname"));
        assertEquals(dto.email(), kakaoAccount.get("email"));
    }

    @Test
    void convertUserAttribute_SuccessForGoogleUser() {
        OAuth2User oAuth2User = Mockito.mock(OAuth2User.class);
        Map<String, Object> attribute = Map.of(
                "name", "testGoogle",
                "email", "test@google.com"
        );

        when(oAuth2User.getAttributes()).thenReturn(attribute);

        AuthUserDto dto = oAuth2AttributeProvider.convertUserAttribute("google", oAuth2User);

        assertNotNull(dto);
        assertEquals(dto.name(), attribute.get("name"));
        assertEquals(dto.email(), attribute.get("email"));
    }

    @Test
    void convertUserAttribute_UnknownRegistrationId() {
        OAuth2User oAuth2User = Mockito.mock(OAuth2User.class);

        AuthUserDto dto = oAuth2AttributeProvider.convertUserAttribute("unknown", oAuth2User);

        assertNull(dto);
    }
}