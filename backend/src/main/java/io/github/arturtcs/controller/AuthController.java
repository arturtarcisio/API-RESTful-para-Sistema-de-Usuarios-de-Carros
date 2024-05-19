package io.github.arturtcs.controller;

import io.github.arturtcs.model.dto.LoginRequestDTO;
import io.github.arturtcs.model.dto.LoginResponseDTO;
import io.github.arturtcs.model.dto.UserTokenDTO;
import io.github.arturtcs.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for user authentication-related operations.
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    private final static String TYPE_TOKEN = "Bearer ";

    @Autowired
    private AuthService authService;

    /**
     * Endpoint for user login.
     *
     * @param loginRequestDTO Object containing user login information.
     * @return ResponseEntity containing the authentication token and user information.
     */
    @Operation(description = "Sign In")
    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDTO> login (@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponseDTO = authService.login(loginRequestDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }

    /**
     * Endpoint for obtaining information about the authenticated user.
     *
     * @param token User authentication token.
     * @return ResponseEntity containing information about the authenticated user.
     */
    @Operation(description = "Sign In")
    @GetMapping("/me")
    public ResponseEntity<UserTokenDTO> infoAboutUserLogger (@RequestHeader("Authorization") String token) {
        var infoUser = authService.findTokenOwner(token.replace(TYPE_TOKEN, ""));
        var userTokenDTO = UserTokenDTO.builder()
                .firstName(infoUser.getFirstName())
                .lastName(infoUser.getLastName())
                .login(infoUser.getLogin())
                .email(infoUser.getEmail())
                .birthday(infoUser.getBirthday())
                .phone(infoUser.getPhone())
                .cars(infoUser.getCars())
                .createdAt(infoUser.getCreatedAt())
                .lastLogin(infoUser.getLastLogin())
                .build();
        return ResponseEntity.ok(userTokenDTO);
    }

}
