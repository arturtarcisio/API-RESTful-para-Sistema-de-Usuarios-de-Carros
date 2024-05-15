package io.github.arturtcs.service;

import io.github.arturtcs.model.User;
import io.github.arturtcs.model.dto.LoginRequestDTO;
import io.github.arturtcs.model.dto.LoginResponseDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> showUsers();
    User registerUser(User user);
    Optional<User> findUserById(Long id);
    void deleteUser(Long id);
    User updateUser(Long id, User user);
    LoginResponseDTO findByLogin(LoginRequestDTO loginRequestDTO);
}
