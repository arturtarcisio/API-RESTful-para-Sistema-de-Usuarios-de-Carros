package io.github.arturtcs.controller;

import io.github.arturtcs.model.dto.LoginRequestDTO;
import io.github.arturtcs.model.dto.LoginResponseDTO;
import io.github.arturtcs.model.dto.UserTokenDTO;
import io.github.arturtcs.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @Operation(description = "Sign In")
    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDTO> login (@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponseDTO = tokenService.findByLogin(loginRequestDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }

    @Operation(description = "Return info about user owner token")
    @GetMapping("/me")
    public ResponseEntity<UserTokenDTO> getUserProfile(@RequestHeader("Authorization") String token) {
        var userToken = tokenService.extractUserInfo(token.replace("Bearer ", ""));
        return ResponseEntity.ok(UserTokenDTO.builder()
                        .firstName(userToken.getFirstName())
                        .lastName(userToken.getLastName())
                        .login(userToken.getLogin())
                        .email(userToken.getEmail())
                        .birthday(userToken.getBirthday())
                        .phone(userToken.getPhone())
                        .cars(userToken.getCars())
                        .createdAt(userToken.getCreatedAt())
                        .lastLogin(userToken.getLastLogin())
                .build());
    }
}
