package io.github.arturtcs.controller;

import io.github.arturtcs.model.User;
import io.github.arturtcs.service.CarServiceImpl;
import io.github.arturtcs.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<User>> listarTodosOsUsuarios() {
        return ResponseEntity.ok(userService.showUsers());
    }

    @PostMapping
    public ResponseEntity<User> cadastrarUsuario (@RequestBody User user) {
        user = userService.registerUser(user);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(uri).body(user);
    }
}
