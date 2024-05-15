package io.github.arturtcs.controller;

import io.github.arturtcs.model.dto.LoginRequestDTO;
import io.github.arturtcs.model.dto.LoginResponseDTO;
import io.github.arturtcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signin")
public class TokenController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login (@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponseDTO = userService.findByLogin(loginRequestDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }
}
