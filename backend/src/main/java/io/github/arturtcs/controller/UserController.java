package io.github.arturtcs.controller;

import io.github.arturtcs.model.User;
import io.github.arturtcs.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controller class for handling operations related to users.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Endpoint to return all users.
     *
     * @return List of all users.
     */
    @Operation(description = "Return all users.")
    @GetMapping("/users")
    public List<User> returnAllUser() {
        return userService.showUsers();
    }

    /**
     * Endpoint to register a new user.
     *
     * @param user User object to be registered.
     * @return ResponseEntity containing the registered user.
     */
    @Operation(description = "Register an user.")
    @PostMapping("/users")
    public ResponseEntity<User> registerAnUser (@RequestBody @Valid User user) {
        user = userService.registerUser(user);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(uri).body(user);
    }

    /**
     * Endpoint to search for a user by ID.
     *
     * @param id ID of the user to search for.
     * @return User object with the specified ID.
     * @throws ResponseStatusException with HTTP status NOT_FOUND if user is not found.
     */
    @Operation(description = "Search an user by id")
    @GetMapping("/users/{id}")
    public User findById (@PathVariable Long id) {
        return userService.findUserById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") );
    }

    /**
     * Endpoint to delete a user by ID.
     *
     * @param id ID of the user to delete.
     */
    @Operation(description = "Delete an user by id")
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById (@PathVariable Long id) {
        userService.deleteUser(id);
    }

    /**
     * Endpoint to update a user by ID.
     *
     * @param id          ID of the user to update.
     * @param userUpdated Updated User object.
     */
    @Operation(description = "Update an user by id")
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable Long id, @RequestBody @Valid User userUpdated) {
        userService.updateUser(id, userUpdated);
    }
}
