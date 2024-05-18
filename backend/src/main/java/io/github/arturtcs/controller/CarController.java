package io.github.arturtcs.controller;

import io.github.arturtcs.model.Car;
import io.github.arturtcs.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controller class for handling operations related to cars.
 */
@RestController
@RequestMapping("api/cars")
public class CarController {

    private final static String TYPE_TOKEN = "Bearer ";

    @Autowired
    private CarService carService;

    /**
     * Endpoint to return all cars for the logged-in user.
     *
     * @param token Authentication token.
     * @return ResponseEntity containing a list of cars owned by the logged-in user.
     */
    @Operation(description = "Return all cars.")
    @GetMapping
    public ResponseEntity<List<Car>> returnAllCars(@RequestHeader("Authorization") String token) {
        var cars = carService.returnAllCarsById(token.replace(TYPE_TOKEN, ""));
        return ResponseEntity.ok(cars);
    }

    /**
     * Endpoint to register a car for the logged-in user.
     *
     * @param token Authentication token.
     * @param car   Car object to be registered.
     * @return ResponseEntity containing the registered car.
     */
    @Operation(description = "Register a car for logged user")
    @PostMapping
    public ResponseEntity<Car> registerCar(@RequestHeader("Authorization") String token, @RequestBody @Valid Car car) {
        car = carService.registerCar(token.replace(TYPE_TOKEN, ""), car);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(car.getId())
                .toUri();
        return ResponseEntity.created(uri).body(car);
    }

    /**
     * Endpoint to search for a car by its ID for the logged-in user.
     *
     * @param token Authentication token.
     * @param id    ID of the car to search for.
     * @return ResponseEntity containing the car with the specified ID.
     */
    @Operation(description = "Search car by id for logged user")
    @GetMapping("/{id}")
    public ResponseEntity<Car> returnCarById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        var car = carService.returnCarById(token.replace(TYPE_TOKEN, ""), id);
        return ResponseEntity.ok(car);
    }

    /**
     * Endpoint to delete a car owned by the logged-in user.
     *
     * @param token Authentication token.
     * @param id    ID of the car to delete.
     * @return ResponseEntity indicating success or failure of the deletion operation.
     */
    @Operation(description = "Delete a car of logged user")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById (@RequestHeader("Authorization") String token, @PathVariable Long id) {
        carService.removeACarOfUserLogged(token.replace(TYPE_TOKEN, ""), id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint to update a car owned by the logged-in user.
     *
     * @param token Authentication token.
     * @param id    ID of the car to update.
     * @param car   Updated car object.
     * @return ResponseEntity indicating success or failure of the update operation.
     */
    @Operation(description = "Update a car of logged user")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCarUserLogged (@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody @Valid Car car) {
        carService.updateCarUserLogged(token.replace(TYPE_TOKEN, ""), id, car);
        return ResponseEntity.noContent().build();
    }

}
