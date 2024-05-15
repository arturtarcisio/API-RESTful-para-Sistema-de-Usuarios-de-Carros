package io.github.arturtcs.service.impl;

import io.github.arturtcs.model.Car;
import io.github.arturtcs.model.User;
import io.github.arturtcs.repository.UserRepository;
import io.github.arturtcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarServiceImpl carService;

    @Override
    public List<User> showUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(User user) {
        removerCarFromUserIfAlreadyExistsInDatabase(user);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository
                .findById(id)
                .map( user -> {
                    userRepository.delete(user);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") );
    }

    private void removerCarFromUserIfAlreadyExistsInDatabase(User user) {
        List<Car> carsAlreadySaved = new ArrayList<>();
        if (!user.getCars().isEmpty()) {
            user.getCars().forEach(car -> {
                if (carService.verifyCarExistBylicensePlate(car.getLicensePlate()) == null) {
                    carService.saveCar(car);
                } else {
                    carsAlreadySaved.add(car);
                }
            });
        }
        user.getCars().removeAll(carsAlreadySaved);
    }

}
