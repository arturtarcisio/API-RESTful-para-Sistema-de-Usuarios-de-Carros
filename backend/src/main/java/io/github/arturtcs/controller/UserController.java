package io.github.arturtcs.controller;

import io.github.arturtcs.model.User;
import io.github.arturtcs.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(description = "Return all users.")
    @GetMapping("/users")
    public List<User> listarTodosOsUsuarios() {
        return userService.showUsers();
    }

    @Operation(description = "Register an user.")
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User cadastrarUsuario (@RequestBody @Valid User user) {
        return userService.registerUser(user);
    }

    @Operation(description = "Search an user by id")
    @GetMapping("/users/{id}")
    public User findById (@PathVariable Long id) {
        return userService.findUserById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") );
    }

    @Operation(description = "Delete an user by id")
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById (@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @Operation(description = "Update an user by id")
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable @Valid Long id, @RequestBody User userUpdated) {
        userService.updateUser(id, userUpdated);
    }
}
