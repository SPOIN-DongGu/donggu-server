package com.donggu.server.domain.auth.provider;

import com.donggu.server.domain.auth.token.AccessToken;
import com.donggu.server.domain.user.domain.Role;
import com.donggu.server.domain.user.domain.User;
import com.donggu.server.global.exception.CustomException;
import com.donggu.server.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(MockitoExtension.class)
class AuthTokenProviderTest {

    private AuthTokenProvider authTokenProvider;

    @Value("${jwt.secret}")
    private String secret = "testsecretkeyforjwttokentestpleasepassthistest";
    private User testUser;

    @BeforeEach
    void setup() {
        authTokenProvider = new AuthTokenProvider(secret);
        ReflectionTestUtils.setField(authTokenProvider, "ACCESS_EXPIRATION_TIME", 300000L);

        testUser = User.builder()
                .id(1L)
                .email("testEmail")
                .role(Role.ROLE_USER)
                .build();
    }

    @Test
    void generateAccessToken_givenUserThenSuccess() {
        AccessToken token = authTokenProvider.generateAccessToken(testUser);

        assertThat(token.token()).isNotEmpty();
        assertEquals(testUser.getEmail(), authTokenProvider.getSubject(token.token()));
        assertTrue(authTokenProvider.validateToken(token.token()));
    }

    @Test
    void validateToken_Success() {
        AccessToken token = authTokenProvider.generateAccessToken(testUser);

        assertTrue(authTokenProvider.validateToken(token.token()));
    }

    @Test
    void validateToken_ThrowExpiredJwtException() throws InterruptedException {
        ReflectionTestUtils.setField(authTokenProvider, "ACCESS_EXPIRATION_TIME", 100L);
        AccessToken token = authTokenProvider.generateAccessToken(testUser);

        Thread.sleep(200L);

        CustomException exception = assertThrows(CustomException.class, () -> authTokenProvider.validateToken(token.token()));
        assertEquals(exception.getErrorCode(), ErrorCode.EXPIRED_TOKEN);
    }

    @Test
    void validateToken_ThrowException() {
        String token = "InvalidToken";

        CustomException exception = assertThrows(CustomException.class, () -> authTokenProvider.validateToken(token));
        assertEquals(exception.getErrorCode(), ErrorCode.INVALID_TOKEN);
    }

    @Test
    void getSubject() {
        AccessToken token = authTokenProvider.generateAccessToken(testUser);

        assertEquals(authTokenProvider.getSubject(token.token()), testUser.getEmail());
    }
}