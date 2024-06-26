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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Implementation of UserService interface providing user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarService carService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Retrieves all users from the database.
     *
     * @return List of all users.
     */
    @Override
    public List<User> showUsers() {
        return userRepository.findAll();
    }

    /**
     * Registers a new user in the system.
     *
     * @param user The user to be registered.
     * @return The registered user.
     * @throws ResponseStatusException if the user registration fails due to invalid data or existing user.
     */
    @Override
    public User registerUser(User user) throws ResponseStatusException {
        validateStringOnlyLetters(user.getFirstName(), "firstName");
        validateStringOnlyLetters(user.getLastName(), "lastName");
        validateEmail(user.getEmail());
        validatePhone(user.getPhone());
        verifyIfEmailAlreadyExist(user.getEmail());
        verifyIfLoginAlreadyExist(user.getLogin());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDate.now());

        if(!user.getCars().isEmpty()){
            user.getCars().forEach( car -> {
                carService.registerCarUser(car);
            });
        }

        return userRepository.save(user);
    }

    /**
     * Finds a user by ID.
     *
     * @param id The ID of the user to find.
     * @return An optional containing the user if found, otherwise empty.
     */
    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Deletes a user by ID.
     *
     * @param id The ID of the user to delete.
     * @throws ResponseStatusException if the user is not found.
     */
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

    /**
     * Updates a user's information.
     *
     * @param id          The ID of the user to update.
     * @param userUpdated The updated user information.
     * @return The updated user.
     * @throws ResponseStatusException if the user is not found or update fails due to invalid data.
     */
    @Override
    public User updateUser(Long id, User userUpdated) {
        return userRepository.findById(id).map(user -> {
            userUpdated.setId(user.getId());

            validateStringOnlyLetters(userUpdated.getFirstName(), "firstName");
            user.setFirstName(userUpdated.getFirstName());

            validateStringOnlyLetters(userUpdated.getLastName(), "lastName");
            user.setLastName(userUpdated.getLastName());

            validateEmail(userUpdated.getEmail());
            verifyIfEmailAlreadyExistForOtherUser(userUpdated.getEmail(), id);
            user.setEmail(userUpdated.getEmail());

            verifyIfLoginAlreadyExistForOtherUser(userUpdated.getLogin(), id);
            user.setLogin(userUpdated.getLogin());

            if (!userUpdated.getPassword().equals(user.getPassword())) {
                user.setPassword(passwordEncoder.encode(userUpdated.getPassword()));
            }

            validatePhone(userUpdated.getPhone());
            user.setPhone(userUpdated.getPhone());

            return userRepository.save(user);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }



    private void verifyIfEmailAlreadyExistForOtherUser(String email, Long userId) {
        userRepository.findByEmail(email)
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(userId)) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists for another user");
                    }
                });
    }

    private void verifyIfLoginAlreadyExistForOtherUser(String login, Long userId) {
        userRepository.findByLogin(login)
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(userId)) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login already exists for another user");
                    }
                });
    }

    private void verifyIfEmailAlreadyExist(String email) {
        var emailExists = userRepository.findByEmail(email);
        if(emailExists.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
    }

    private void verifyIfLoginAlreadyExist(String login) {
        var loginExists = userRepository.findByLogin(login);
        if(loginExists.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login already exists");
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
