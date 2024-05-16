package io.github.arturtcs.service;

import io.github.arturtcs.model.User;
import io.github.arturtcs.model.dto.LoginRequestDTO;
import io.github.arturtcs.model.dto.LoginResponseDTO;

public interface TokenService {
    LoginResponseDTO findByLogin(LoginRequestDTO loginRequestDTO);
    User extractUserInfo(String token);
}
