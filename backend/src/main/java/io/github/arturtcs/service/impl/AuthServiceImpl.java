package io.github.arturtcs.service.impl;

import io.github.arturtcs.model.User;
import io.github.arturtcs.model.dto.LoginRequestDTO;
import io.github.arturtcs.model.dto.LoginResponseDTO;
import io.github.arturtcs.repository.UserRepository;
import io.github.arturtcs.service.AuthService;
import io.github.arturtcs.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Service implementation for authentication-related operations.
 */
@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private AuthenticationManager authenticationManager;

    /**
     * Loads a user by username from the repository.
     *
     * @param login The username to search for.
     * @return The user with the specified username.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public User loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
    }

    /**
     * Performs user authentication and generates a JWT token.
     *
     * @param loginRequestDTO The login request DTO containing login credentials.
     * @return The login response DTO containing the JWT token and user information.
     */
    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        authenticationManager = context.getBean(AuthenticationManager.class);
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.login(), loginRequestDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = jwtService.generateToken((User) auth.getPrincipal());
        var user = loadUserByUsername(loginRequestDTO.login());
        user.setLastLogin(LocalDate.now());
        userRepository.save(user);
        return new LoginResponseDTO(token, jwtService.getExpirationDate(), user.getLogin(), user.getEmail());
    }

    /**
     * Finds the owner of the given JWT token.
     *
     * @param token The JWT token.
     * @return The user who owns the token.
     */
    @Override
    public User findTokenOwner(String token) {
        var subject = jwtService.validateToken(token);
        if (subject == null || subject.isEmpty()) {
            log.warn("Token validation failed for token: {}", token);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        return userRepository.findByLogin(subject).orElseThrow(() -> {
            log.warn("No user found for token: {}", token);
            return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        });
    }
}
