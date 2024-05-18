package io.github.arturtcs.service;

import io.github.arturtcs.model.User;

import java.time.Instant;

public interface JwtService {

    String generateToken(User user);
    String validateToken(String token);
    Instant getExpirationDate();

}
