package io.github.arturtcs.service.impl;

import io.github.arturtcs.model.User;
import io.github.arturtcs.model.dto.LoginRequestDTO;
import io.github.arturtcs.model.dto.LoginResponseDTO;
import io.github.arturtcs.repository.UserRepository;
import io.github.arturtcs.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private ApplicationContext context;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;
    private LoginRequestDTO loginRequestDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("testUser");
        user.setEmail("test@example.com");
        user.setLastLogin(Instant.now());

        loginRequestDTO = new LoginRequestDTO("testUser", "password");

        // Remova esta linha se não for usada em todos os testes
        lenient().when(context.getBean(AuthenticationManager.class)).thenReturn(authenticationManager);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        when(userRepository.findByLogin("testUser")).thenReturn(Optional.of(user));

        User result = authService.loadUserByUsername("testUser");

        assertThat(result).isEqualTo(user);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByLogin("testUser")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.loadUserByUsername("testUser"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }

    @Test
    void testLogin_Success() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");
        when(jwtService.getExpirationDate()).thenReturn(Instant.now().plusSeconds(3600));
        when(userRepository.findByLogin("testUser")).thenReturn(Optional.of(user));

        LoginResponseDTO response = authService.login(loginRequestDTO);

        assertThat(response.acessToken()).isEqualTo("jwtToken");
        assertThat(response.userLogged()).isEqualTo("testUser");
        assertThat(response.userEmail()).isEqualTo("test@example.com");

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testLogin_Failure() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));

        assertThatThrownBy(() -> authService.login(loginRequestDTO))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Unauthorized");
    }

    @Test
    void testFindTokenOwner_TokenValid() {
        when(jwtService.validateToken("validToken")).thenReturn("testUser");
        when(userRepository.findByLogin("testUser")).thenReturn(Optional.of(user));

        User result = authService.findTokenOwner("validToken");

        assertThat(result).isEqualTo(user);
    }

    @Test
    void testFindTokenOwner_TokenInvalid() {
        when(jwtService.validateToken("invalidToken")).thenReturn("testUser");
        when(userRepository.findByLogin("testUser")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.findTokenOwner("invalidToken"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.NOT_FOUND.getReasonPhrase());
    }
}
