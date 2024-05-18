package io.github.arturtcs.service;

import io.github.arturtcs.model.User;
import io.github.arturtcs.model.dto.LoginRequestDTO;
import io.github.arturtcs.model.dto.LoginResponseDTO;

/**
 * AuthService interface defines methods related to user authentication and token management.
 */
public interface AuthService {

    /**
     * Authenticates a user with the provided login credentials.
     *
     * @param loginRequestDTO The login request containing user credentials.
     * @return A response containing authentication details, including a token.
     */
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    /**
     * Finds the user associated with the provided authentication token.
     *
     * @param token The authentication token.
     * @return The user associated with the token, if found.
     */
    User findTokenOwner(String token);
}
