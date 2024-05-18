package io.github.arturtcs.service.impl;

import io.github.arturtcs.model.Car;
import io.github.arturtcs.repository.CarRepository;
import io.github.arturtcs.service.AuthService;
import io.github.arturtcs.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private AuthService authService;

    @Override
    public List<Car> returnAllCarsById(String token) {
        var user = authService.findTokenOwner(token);
        return user.getCars();
    }

    /**
     * Registers a new car for the user logged
     *
     * @param token Token of user logged.
     * @param car The car to be registered for user logeed.
     */
    @Override
    public Car registerCar(String token, Car car) {
        var user = authService.findTokenOwner(token);
        verifyIfCarExists(car);
        verifyIfLicensePlateIsValid(car);
        car.setUserOwner(user);
        return carRepository.save(car);
    }

    /**
     * Registers a new car.
     *
     * @param car The car to be registered.
     */
    @Override
    public void registerCarUser(Car car) {
        verifyIfLicensePlateIsValid(car);
        verifyIfCarExists(car);
        carRepository.save(car);
    }

    /**
     * Remove a car of user logged
     *
     * @param token Token of user logged
     * @param id Id of car will deleted
     */
    @Override
    public void removeACarOfUserLogged(String token, Long id) {
        var user = authService.findTokenOwner(token);
        var carroOptional = carRepository.findById(id);

        if (carroOptional.isPresent()) {
            var carro = carroOptional.get();
            if (user.getCars().contains(carro)) {
                user.getCars().remove(carro);
                carRepository.delete(carro);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You don't have this car in your list.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You don't have this car in your list.");
        }
    }

    /**
     * Update a car.
     *
     * @param token Token of user logged
     * @param id Id of car will deleted
     * @param car Car updated
     */
    @Override
    public void updateCarUserLogged(String token, Long id, Car car) {
        var user = authService.findTokenOwner(token);
        Optional<Car> optionalCar = user.getCars().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        Car existingCar = optionalCar.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found"));

        verifyIfCarExists(car);
        verifyIfLicensePlateIsValid(car);

        existingCar.setCarYear(car.getCarYear());
        existingCar.setColor(car.getColor());
        existingCar.setLicensePlate(car.getLicensePlate());
        existingCar.setModel(car.getModel());

        carRepository.save(existingCar);
    }

    /**
     * Return a car of user logged
     *
     * @param token Token of user logged
     * @param id Id of car of user logged
     */
    @Override
    public Car returnCarById(String token, Long id) {
        var user = authService.findTokenOwner(token);
        Optional<Car> optionalCar = user.getCars().stream()
                .filter(car -> car.getId().equals(id))
                .findFirst();
        return optionalCar.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found"));
    }

    /**
     * Verifies if the license plate of the provided car is valid.
     *
     * @param car The car to be validated.
     */
    private void verifyIfLicensePlateIsValid(Car car) {
        String regexLicensePlate = "^[A-Z]{2,3}-\\d{4}|[A-Z]{3}-\\d{1}[A-Z]{1}\\d{2}$";
        Pattern pattern = Pattern.compile(regexLicensePlate);
        Matcher matcher = pattern.matcher(car.getLicensePlate());
        if (car.getLicensePlate().matches("[0-9]+") || !matcher.matches())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid fields: License Plate");
    }

    /**
     * Verifies if the car with the provided license plate already exists.
     *
     * @param car The car to be checked.
     */
    private void verifyIfCarExists(Car car) {
        car = carRepository.findBylicensePlate(car.getLicensePlate());
        if (car != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "License plate already exists");
    }


}
