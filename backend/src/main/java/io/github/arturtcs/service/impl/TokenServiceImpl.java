package io.github.arturtcs.service.impl;

import io.github.arturtcs.model.User;
import io.github.arturtcs.model.dto.LoginRequestDTO;
import io.github.arturtcs.model.dto.LoginResponseDTO;
import io.github.arturtcs.repository.UserRepository;
import io.github.arturtcs.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;

    public LoginResponseDTO findByLogin(LoginRequestDTO loginRequestDTO) {
        var user = userRepository.findByLogin(loginRequestDTO.login());
        if (user == null || !user.isLoginCorrect(loginRequestDTO, passwordEncoder)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid login or password");
        }

        var now = Instant.now();
        var expiresIn = 10L;

        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(String.valueOf(user.getId()))
                .expiresAt(now.plusSeconds(expiresIn))
                .issuedAt(now)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponseDTO(jwtValue, expiresIn, user.getFirstName(), user.getEmail());
    }

    @Override
    public User extractUserInfo(String token) {
        try{
            if(StringUtils.isEmpty(token))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: Missing token");

             var claims = extractAllClaims(token);
            var userId = claims.getSubject();
            return userRepository.findById(Long.valueOf(userId)).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized - invalid session");
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

//    public boolean isTokenValid(String token) {
//        var claims = extractAllClaims(token);
//        var userId = claims.getSubject();
//        var user = userRepository.findById(Long.valueOf(userId)).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
//        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }

}
