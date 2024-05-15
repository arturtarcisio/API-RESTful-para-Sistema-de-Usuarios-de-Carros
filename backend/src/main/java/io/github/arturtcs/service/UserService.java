package io.github.arturtcs.service;

import io.github.arturtcs.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> showUsers();
    User registerUser(User user);
    Optional<User> findUserById(Long id);
    void deleteUser(Long id);
}
