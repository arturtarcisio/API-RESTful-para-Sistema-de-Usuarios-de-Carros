package io.github.arturtcs.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.arturtcs.model.User;
import io.github.arturtcs.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

/**
 * Implementation of JwtService interface providing JWT (JSON Web Token) related operations.
 */
@Service
public class JwtServiceImpl implements JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtServiceImpl.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiresIn;

    /**
     * Generates a JWT token for the provided user.
     *
     * @param user The user for whom the token is generated.
     * @return The generated JWT token.
     * @throws RuntimeException if an error occurs during token generation.
     */
    @Override
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("auth")
                    .withSubject(user.getLogin())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("ERROR WHILE GENERATING TOKEN", exception);
        }
    }

    /**
     * Validates the provided JWT token and retrieves the subject (login) from it.
     *
     * @param token The JWT token to be validated.
     * @return The subject (login) extracted from the token if valid, otherwise an empty string.
     */
    @Override
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("auth")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            log.error("Invalid token: {}", token, exception);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token", exception);
        }
    }


    /**
     * Retrieves the expiration date of the JWT token.
     *
     * @return The expiration date of the JWT token.
     */
    @Override
    public Instant getExpirationDate() {
        return Instant.now().plusSeconds(expiresIn);
    }
}
