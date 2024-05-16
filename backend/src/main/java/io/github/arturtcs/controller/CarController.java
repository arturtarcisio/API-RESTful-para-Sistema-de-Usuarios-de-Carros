package io.github.arturtcs.controller;

import io.github.arturtcs.model.Car;
import io.github.arturtcs.model.User;
import io.github.arturtcs.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/cars/")
public class CarController {

    private final static String TYPE_TOKEN = "Bearer ";

    @Autowired
    private CarService carService;

    @Operation(description = "Return all cars.")
    @GetMapping
    public ResponseEntity<List<Car>> returnAllCars(@RequestHeader("Authorization") String token) {
        var cars = carService.returnAllCarsById(token.replace(TYPE_TOKEN, ""));
        return ResponseEntity.ok(cars);
    }

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


}
