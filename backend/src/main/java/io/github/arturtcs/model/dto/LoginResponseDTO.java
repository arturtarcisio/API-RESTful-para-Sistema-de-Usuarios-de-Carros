package io.github.arturtcs.model.dto;

public record LoginResponseDTO(String acessToken, Long expiresIn, String userLogged, String userEmail) {
}
