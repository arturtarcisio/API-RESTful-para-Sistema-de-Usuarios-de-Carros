package io.github.arturtcs;


import io.github.arturtcs.model.Car;
import io.github.arturtcs.model.User;
import io.github.arturtcs.repository.CarRepository;
import io.github.arturtcs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

/**
 * The main entry point of the application.
 */
@SpringBootApplication
public class App implements CommandLineRunner
{
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Method to execute the command line runner.
     *
     * @param args Command line arguments
     * @throws Exception Exception if there is an error during execution
     */
    @Override
    public void run(String... args) throws Exception {

        User user1 = new User(1L,"Artur", "Silva", "artur@email.com",  LocalDate.of(1993, 01, 14), "atcs", passwordEncoder.encode("h3ll0"), "81983351902", null, LocalDate.now(), null);
        User user2 = new User(2L,"Maria", "Silva", "maria@email.com", LocalDate.of(1990, 12, 31), "mcsf", passwordEncoder.encode("h3ll038475"), "81975843837", null, LocalDate.now(), null);

        userRepository.saveAll(Arrays.asList(user1, user2));

        Car car1 = new Car(1L,1982, "MUE-2440", "GOL", "BRANCO", user1);
        Car car2 = new Car(2L, 1982, "HPP-1452", "GOL", "PRETO", user1);
        Car car3 = new Car(3L, 1990, "NBV-11535", "SIENA", "PRATA", user1);
        Car car4 = new Car(4L, 2000, "NBH-3S35", "AUDI", "VINHO", user1);
        Car car5 = new Car(5L, 1922, "AAE-3C35", "VECTRA", "AZUL", user1);
        Car car6 = new Car(6L, 1932, "PEA-3A35", "COROLA", "AMARELO", user1);
        Car car7 = new Car(7L, 1923, "QYL-3A35", "ASTRA", "MARROM", user1);
        Car car8 = new Car(8L,2022, "IAP-2847", "ONIX", "BRANCO", user2);
        Car car9 = new Car(9L,1971, "EWT-9276", "FUSCA", "AMARELO", user2);
        carRepository.saveAll(Arrays.asList(car1, car2, car3, car4, car5, car6, car7, car8, car9));


    }
}
