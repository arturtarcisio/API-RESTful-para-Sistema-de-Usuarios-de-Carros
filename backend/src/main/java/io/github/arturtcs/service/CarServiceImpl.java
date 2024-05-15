package io.github.arturtcs.service;

import io.github.arturtcs.model.Car;
import io.github.arturtcs.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService{

    @Autowired
    private CarRepository carRepository;

    @Override
    public Car verifyCarExistBylicensePlate(String licensePlate) {
        return carRepository.findBylicensePlate(licensePlate);
    }

    @Override
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }


}
