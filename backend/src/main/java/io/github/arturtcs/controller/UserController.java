package io.github.arturtcs.controller;

import io.github.arturtcs.model.User;
import io.github.arturtcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> listarTodosOsUsuarios() {
        return userService.showUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User cadastrarUsuario (@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/{id}")
    public User findById (@PathVariable Long id) {
        return userService
                .findUserById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado") );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById (@PathVariable Long id) {
        userService.deleteUser(id);
    }


}
