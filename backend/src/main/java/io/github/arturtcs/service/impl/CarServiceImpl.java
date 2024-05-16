package io.github.arturtcs.service.impl;

import io.github.arturtcs.model.Car;
import io.github.arturtcs.model.User;
import io.github.arturtcs.repository.CarRepository;
import io.github.arturtcs.service.CarService;
import io.github.arturtcs.service.TokenService;
import io.github.arturtcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Override
    public Car registerCar(String token, Car car) {
        var user = tokenService.extractUserInfo(token);
        verifyIfCarExists(car);
        verifyIfLicensePlateIsValid(car);
        car.setUserOwner(user);
        return carRepository.save(car);
    }

    @Override
    public Car returnCarById(String token, Long id) {
        var user = tokenService.extractUserInfo(token);
        Optional<Car> optionalCar = user.getCars().stream()
                .filter(car -> car.getId().equals(id))
                .findFirst();
        return optionalCar.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found"));
    }

    private void verifyIfLicensePlateIsValid(Car car) {
        String regexLicensePlate = "^[A-Z]{3}-\\d{4}$";
        Pattern pattern = Pattern.compile(regexLicensePlate);
        Matcher matcher = pattern.matcher(car.getLicensePlate());
        if (car.getLicensePlate().matches("[0-9]+") || !matcher.matches())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid fields: Liscense Plate");
    }

    private void verifyIfCarExists(Car car) {
        List<Car> carsAlreadySaved = new ArrayList<>();
        car = carRepository.findBylicensePlate(car.getLicensePlate());
        if (car != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "License plate already exists.");
    }


}
