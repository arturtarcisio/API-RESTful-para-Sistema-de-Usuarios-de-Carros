package io.github.arturtcs.controller;

import io.github.arturtcs.model.Car;
import io.github.arturtcs.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/cars/")
public class CarController {

    @Autowired
    private CarService carService;

    @Operation(description = "Return all cars.")
    @GetMapping
    public List<Car> returnAllCars(@RequestHeader("Authorization") String token) {
        return carService.returnAllCarsById(token.replace("Bearer ", ""));
    }


}
