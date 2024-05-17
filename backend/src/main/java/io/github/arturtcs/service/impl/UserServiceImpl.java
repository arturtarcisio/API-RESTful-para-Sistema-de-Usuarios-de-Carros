package io.github.arturtcs.service.impl;

import io.github.arturtcs.model.Car;
import io.github.arturtcs.model.User;
import io.github.arturtcs.repository.UserRepository;
import io.github.arturtcs.service.CarService;
import io.github.arturtcs.service.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarService carService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<User> showUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(User user) throws ResponseStatusException {
        validateStringOnlyLetters(user.getFirstName(), "firstName");
        validateStringOnlyLetters(user.getLastName(), "lastName");
        validateEmail(user.getEmail());
        validatePhone(user.getPhone());
        verifyIfEmailAlreadyExist(user);
        verifyIfLoginAlreadyExist(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(Instant.now());
        List<Car> cars = user.getCars();
        user.setCars(new ArrayList<>());
        User userSaved = userRepository.save(user);
        if (cars != null && !cars.isEmpty()) {
            cars.forEach(car -> {
                car.setUserOwner(userSaved);
                carService.registerCarUser(car);
                userSaved.getCars().add(car);
            });
        }
        return userSaved;
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

    @Override
    public User updateUser(Long id, User userUpdated) {
        return userRepository
                .findById(id)
                .map( user -> {
                    userUpdated.setId(user.getId());
                    validateStringOnlyLetters(userUpdated.getFirstName(), "firstName");
                    user.setFirstName(userUpdated.getFirstName());

                    validateStringOnlyLetters(userUpdated.getLastName(), "lastName");
                    user.setLastName(userUpdated.getLastName());

                    validateEmail(userUpdated.getEmail());
                    verifyIfEmailAlreadyExist(userUpdated);
                    user.setEmail(userUpdated.getEmail());

                    verifyIfLoginAlreadyExist(userUpdated);
                    user.setLogin(userUpdated.getLogin());

                    user.setPassword(passwordEncoder.encode(userUpdated.getPassword()));

                    validatePhone(userUpdated.getPhone());
                    user.setPhone(userUpdated.getPhone());
                    user.getCars().removeAll(user.getCars());
                    var userSaved = userRepository.save(user);

                    if( !userUpdated.getCars().isEmpty() ){
                        userUpdated.getCars().forEach( car -> {
                            car.setUserOwner(userSaved);
                            if (StringUtils.isEmpty(car.getLicensePlate()))
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing field: License plate");

                            if (StringUtils.isEmpty(car.getModel()))
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing field: Car model");
                            carService.registerCarUser(car);
                        });
                    }

                    return userSaved;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private void verifyIfEmailAlreadyExist(User user) {
        User userReturned = userRepository.findByEmail(user.getEmail());
        if (userReturned != null && !userReturned.getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
    }

    private void verifyIfLoginAlreadyExist(User user) {
        User userReturned = userRepository.findByLogin(user.getLogin());
        if (userReturned != null && !userReturned.getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login already exists");
        }
    }

    private static void validateStringOnlyLetters(String value, String fieldName) {
        if (value == null || !value.matches("^[a-zA-ZÀ-ú ]+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid fields: The field " + fieldName + " must contain only letters");
        }
    }

    private static void validateEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid fields: The field email is invalid, must contain '@'.");
        }
    }

    private static void validatePhone(String phone) {
        if (phone == null || !Pattern.matches("\\d{11}", phone)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid fields: The field phone must contain eleven numeric digits.");
        }
    }
}
