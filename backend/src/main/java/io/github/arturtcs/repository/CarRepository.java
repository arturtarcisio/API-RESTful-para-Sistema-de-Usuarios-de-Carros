package io.github.arturtcs.repository;

import io.github.arturtcs.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing car data in the database.
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    /**
     * Finds a car by its license plate.
     *
     * @param licensePlate The license plate of the car to find.
     * @return The car with the specified license plate, or null if not found.
     */
    Car findBylicensePlate(String licensePlate);

}
