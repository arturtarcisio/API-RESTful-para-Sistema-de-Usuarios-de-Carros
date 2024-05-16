package io.github.arturtcs.service.impl;

import io.github.arturtcs.model.Car;
import io.github.arturtcs.repository.CarRepository;
import io.github.arturtcs.service.CarService;
import io.github.arturtcs.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    public Car verifyCarExistBylicensePlate(String licensePlate) {
        return carRepository.findBylicensePlate(licensePlate);
    }

    @Override
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public List<Car> returnAllCarsById(String token) {
        var user = tokenService.extractUserInfo(token);
        return user.getCars();
    }


}
