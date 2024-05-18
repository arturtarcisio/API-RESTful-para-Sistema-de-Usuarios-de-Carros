package io.github.arturtcs.model.dto;

import java.time.Instant;

public record LoginResponseDTO(String acessToken, Instant expiresIn, String userLogged, String userEmail) {
}
