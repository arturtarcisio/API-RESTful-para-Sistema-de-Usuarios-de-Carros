package io.github.arturtcs.repository;

import io.github.arturtcs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing user data in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their login.
     *
     * @param login The login of the user to find.
     * @return An Optional containing the user with the specified login, or empty if not found.
     */
    Optional<User> findByLogin(String login);

    /**
     * Finds a user by their email.
     *
     * @param email The email of the user to find.
     * @return An Optional containing the user with the specified email, or empty if not found.
     */
    Optional<User> findByEmail(String email);

}
