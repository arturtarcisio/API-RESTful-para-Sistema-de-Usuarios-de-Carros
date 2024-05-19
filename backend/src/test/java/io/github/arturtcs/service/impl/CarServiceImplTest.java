package io.github.arturtcs.service.impl;

import io.github.arturtcs.model.Car;
import io.github.arturtcs.model.User;
import io.github.arturtcs.repository.CarRepository;
import io.github.arturtcs.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private CarServiceImpl carService;

    private User user;
    private Car car;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("testUser");
        user.setCars(Collections.emptyList());

        car = new Car();
        car.setId(1L);
        car.setCarYear(2021);
        car.setColor("Red");
        car.setLicensePlate("ABC-1234");
        car.setModel("Model X");
    }

    @Test
    void testReturnAllCarsById() {
        when(authService.findTokenOwner("token")).thenReturn(user);
        List<Car> cars = carService.returnAllCarsById("token");

        assertThat(cars).isEqualTo(user.getCars());
    }

    @Test
    void testRegisterCar_Success() {
        when(authService.findTokenOwner("token")).thenReturn(user);
        when(carRepository.save(any(Car.class))).thenReturn(car);

        Car registeredCar = carService.registerCar("token", car);

        assertThat(registeredCar).isEqualTo(car);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testRegisterCar_InvalidLicensePlate() {
        car.setLicensePlate("1234567"); // Invalid license plate

        when(authService.findTokenOwner("token")).thenReturn(user);

        assertThatThrownBy(() -> carService.registerCar("token", car))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Invalid fields: License Plate");
    }

    @Test
    void testRegisterCar_ExistingLicensePlate() {
        when(authService.findTokenOwner("token")).thenReturn(user);
        when(carRepository.findBylicensePlate(car.getLicensePlate())).thenReturn(car);

        assertThatThrownBy(() -> carService.registerCar("token", car))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("License plate already exists");
    }

    @Test
    void testRemoveACarOfUserLogged_CarExists() {
        user.setCars(List.of(car));
        when(authService.findTokenOwner("token")).thenReturn(user);
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        carService.removeACarOfUserLogged("token", 1L);

        assertThat(user.getCars()).doesNotContain(car);
        verify(carRepository, times(1)).delete(car);
    }

    @Test
    void testRemoveACarOfUserLogged_CarNotExists() {
        when(authService.findTokenOwner("token")).thenReturn(user);
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> carService.removeACarOfUserLogged("token", 1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("You don't have this car in your list");
    }

    @Test
    void testUpdateCarUserLogged_Success() {
        user.setCars(List.of(car));
        Car updatedCar = new Car();
        updatedCar.setId(1L);
        updatedCar.setCarYear(2022);
        updatedCar.setColor("Blue");
        updatedCar.setLicensePlate("XYZ-9876");
        updatedCar.setModel("Model Y");

        when(authService.findTokenOwner("token")).thenReturn(user);
        when(carRepository.save(any(Car.class))).thenReturn(updatedCar);

        carService.updateCarUserLogged("token", 1L, updatedCar);

        assertThat(car.getCarYear()).isEqualTo(2022);
        assertThat(car.getColor()).isEqualTo("Blue");
        assertThat(car.getLicensePlate()).isEqualTo("XYZ-9876");
        assertThat(car.getModel()).isEqualTo("Model Y");

        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testReturnCarById_Success() {
        user.setCars(List.of(car));
        when(authService.findTokenOwner("token")).thenReturn(user);

        Car foundCar = carService.returnCarById("token", 1L);

        assertThat(foundCar).isEqualTo(car);
    }

    @Test
    void testReturnCarById_NotFound() {
        when(authService.findTokenOwner("token")).thenReturn(user);

        assertThatThrownBy(() -> carService.returnCarById("token", 1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Car not found");
    }
}
