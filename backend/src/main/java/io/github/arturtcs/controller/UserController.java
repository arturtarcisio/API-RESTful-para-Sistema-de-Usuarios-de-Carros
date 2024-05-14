package io.github.arturtcs.controller;

import io.github.arturtcs.model.User;
import io.github.arturtcs.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> listarTodosOsCarros() {
        return userService.listarUsuarios();
    }
}
