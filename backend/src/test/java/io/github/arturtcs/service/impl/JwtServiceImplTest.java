package io.github.arturtcs.service.impl;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.arturtcs.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class JwtServiceImplTest {

    @InjectMocks
    private JwtServiceImpl jwtService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("testUser");

        ReflectionTestUtils.setField(jwtService, "secret", "testSecret");
        ReflectionTestUtils.setField(jwtService, "expiresIn", 3600L);
    }

    @Test
    void testGenerateToken_Success() {
        String token = jwtService.generateToken(user);
        assertThat(token).isNotNull();
    }

    @Test
    void testGenerateToken_ThrowsJWTCreationException() {
        try (MockedStatic<com.auth0.jwt.JWT> jwtMockedStatic = org.mockito.Mockito.mockStatic(com.auth0.jwt.JWT.class)) {
            jwtMockedStatic.when(() -> com.auth0.jwt.JWT.create()).thenThrow(JWTCreationException.class);

            assertThatThrownBy(() -> jwtService.generateToken(user))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("ERROR WHILE GENERATING TOKEN");
        }
    }

    @Test
    void testValidateToken_Success() {
        String token = jwtService.generateToken(user);
        String login = jwtService.validateToken(token);
        assertThat(login).isEqualTo(user.getLogin());
    }

    @Test
    void testValidateToken_InvalidToken() {
        String invalidToken = "invalidToken";
        String login = jwtService.validateToken(invalidToken);
        assertThat(login).isEmpty();
    }

    @Test
    void testValidateToken_ThrowsJWTVerificationException() {
        try (MockedStatic<com.auth0.jwt.JWT> jwtMockedStatic = org.mockito.Mockito.mockStatic(com.auth0.jwt.JWT.class)) {
            jwtMockedStatic.when(() -> com.auth0.jwt.JWT.require(any(Algorithm.class)).build().verify(anyString())).thenThrow(JWTVerificationException.class);

            String invalidToken = "invalidToken";
            String login = jwtService.validateToken(invalidToken);
            assertThat(login).isEmpty();
        }
    }


    @Test
    void testGetExpirationDate() {
        Instant expirationDate = jwtService.getExpirationDate();
        assertThat(expirationDate).isAfter(Instant.now());
    }
}
