package io.github.arturtcs.service.impl;

import io.github.arturtcs.model.Car;
import io.github.arturtcs.model.User;
import io.github.arturtcs.repository.UserRepository;
import io.github.arturtcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarServiceImpl carService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<User> showUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(User user) throws ResponseStatusException{
        validateStringOnlyLetters(user.getFirstName(), "firstName");
        validateStringOnlyLetters(user.getLastName(), "lastName");
        validateEmail(user.getEmail());
        validatePhone(user.getPhone());
        verifyIfEmailAlreadyExist(user);
        verifyIfLoginAlreadyExist(user);
        removerCarFromUserIfAlreadyExistsInDatabase(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @Override
    public User updateUser(Long id, User userUpdated) {
        validateStringOnlyLetters(userUpdated.getFirstName(), "firstName");
        validateStringOnlyLetters(userUpdated.getLastName(), "lastName");
        validateEmail(userUpdated.getEmail());
        validatePhone(userUpdated.getPhone());
        verifyIfEmailAlreadyExist(userUpdated);
        verifyIfLoginAlreadyExist(userUpdated);
        return userRepository
                .findById(id)
                .map( user -> {
                    user.setFirstName(userUpdated.getFirstName());
                    user.setLastName(userUpdated.getLastName());
                    user.setEmail(userUpdated.getEmail());
                    user.setLogin(userUpdated.getLogin());
                    user.setPassword(userUpdated.getPassword());
                    user.setPhone(userUpdated.getPhone());
                    return userRepository.save(user);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
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

    private void verifyIfEmailAlreadyExist(User user) {
        User userReturned = userRepository.findByEmail(user.getEmail());
        if (userReturned != null && !userReturned.getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
    }

    private void verifyIfLoginAlreadyExist(User user) {
        User userReturned = userRepository.findByLogin(user.getLogin());
        if (userReturned != null && !userReturned.getLogin().equals(user.getLogin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login already exists");
        }
    }

    private static void validateStringOnlyLetters(String value, String fieldName) {
        if (value == null || !value.matches("^[a-zA-ZÀ-ú]+$")) {
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
