package io.github.arturtcs.service;

import io.github.arturtcs.model.Car;
import io.github.arturtcs.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CarService {
    List<Car> returnAllCarsById(String token);
    Car registerCar(String token, Car car);
    Car returnCarById(String token, Long id);
    void registerCarUser(Car car);
    void removeACarOfUserLogged(String token, Long id);
    void updateCarUserLogged(String token, Long id, Car car);
}
