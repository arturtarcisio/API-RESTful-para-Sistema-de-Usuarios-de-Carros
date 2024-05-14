package io.github.arturtcs.service;

import io.github.arturtcs.model.User;
import io.github.arturtcs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> listarUsuarios() {
        return userRepository.findAll();
    }
}
