package io.github.arturtcs.service;

import io.github.arturtcs.model.Car;
import io.github.arturtcs.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CarService {
    Car verifyCarExistBylicensePlate(String licensePlate);
    Car saveCar(Car car);
    List<Car> returnAllCarsById(String token);
    Car registerCar(String token, Car car);
    Car returnCarById(String token, Long id);
//    List<Car> verifyIfCarExistsAndRemoveFromList(List<Car> cars);
    void registerCarUser(User user);
    void removeACarOfUserLogged(String token, Long id);
}
