package io.github.arturtcs.service;

import io.github.arturtcs.model.Car;
import io.github.arturtcs.model.User;
import io.github.arturtcs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        return userRepository.save(user);
    }

}
