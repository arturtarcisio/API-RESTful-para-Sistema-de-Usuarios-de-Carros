package io.github.arturtcs.service;

import io.github.arturtcs.model.Car;

public interface CarService {
    Car verifyCarExistBylicensePlate(String licensePlate);
    Car saveCar(Car car);
}
