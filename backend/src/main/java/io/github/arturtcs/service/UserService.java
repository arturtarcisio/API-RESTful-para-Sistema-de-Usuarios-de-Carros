package io.github.arturtcs.service;

import io.github.arturtcs.model.User;

import java.util.List;
import java.util.Optional;

/**
 * UserService interface defines methods for managing user-related operations.
 */
public interface UserService {

    /**
     * Retrieves a list of all users.
     *
     * @return A list containing all users.
     */
    List<User> showUsers();

    /**
     * Registers a new user.
     *
     * @param user The user to be registered.
     * @return The registered user.
     */
    User registerUser(User user);

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return An Optional containing the user if found, otherwise empty.
     */
    Optional<User> findUserById(Long id);

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     */
    void deleteUser(Long id);

    /**
     * Updates a user's information.
     *
     * @param id         The ID of the user to update.
     * @param userUpdate The updated user information.
     * @return The updated user.
     */
    User updateUser(Long id, User userUpdate);
}
