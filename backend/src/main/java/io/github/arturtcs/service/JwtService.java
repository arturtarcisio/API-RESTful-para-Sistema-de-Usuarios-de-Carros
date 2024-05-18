package io.github.arturtcs.service;

import io.github.arturtcs.model.User;

import java.time.Instant;

/**
 * JwtService interface defines methods for generating and validating JWT tokens.
 */
public interface JwtService {

    /**
     * Generates a JWT token for the provided user.
     *
     * @param user The user for whom the token is generated.
     * @return The generated JWT token.
     */
    String generateToken(User user);

    /**
     * Validates the provided JWT token.
     *
     * @param token The JWT token to be validated.
     * @return The subject (usually the user login) extracted from the token if it is valid, or an empty string otherwise.
     */
    String validateToken(String token);

    /**
     * Retrieves the expiration date of JWT tokens.
     *
     * @return The expiration date of JWT tokens.
     */
    Instant getExpirationDate();
}
