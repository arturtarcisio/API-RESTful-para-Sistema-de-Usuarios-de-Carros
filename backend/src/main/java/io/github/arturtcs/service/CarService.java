package io.github.arturtcs.service;

import io.github.arturtcs.model.Car;
import io.github.arturtcs.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * CarService interface defines methods for managing cars and their association with users.
 */
public interface CarService {

    /**
     * Retrieves all cars associated with a user identified by the provided token.
     *
     * @param token The authentication token identifying the user.
     * @return A list of cars associated with the user.
     */
    List<Car> returnAllCarsById(String token);

    /**
     * Registers a new car for the user identified by the provided token.
     *
     * @param token The authentication token identifying the user.
     * @param car   The car to be registered.
     * @return The registered car.
     */
    Car registerCar(String token, Car car);

    /**
     * Retrieves a specific car associated with a user identified by the provided token and car ID.
     *
     * @param token The authentication token identifying the user.
     * @param id    The ID of the car to retrieve.
     * @return The car associated with the user and ID.
     */
    Car returnCarById(String token, Long id);

    /**
     * Registers a new car without associating it with any user.
     *
     * @param car The car to be registered.
     */
    void registerCarUser(Car car);

    /**
     * Removes a car associated with the user identified by the provided token and car ID.
     *
     * @param token The authentication token identifying the user.
     * @param id    The ID of the car to be removed.
     */
    void removeACarOfUserLogged(String token, Long id);

    /**
     * Updates a car associated with the user identified by the provided token and car ID.
     *
     * @param token The authentication token identifying the user.
     * @param id    The ID of the car to be updated.
     * @param car   The updated car information.
     */
    void updateCarUserLogged(String token, Long id, Car car);

}
