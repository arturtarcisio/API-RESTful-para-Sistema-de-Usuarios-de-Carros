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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    @Override
    public void run(String... args) throws Exception {
        List<Car> cars = Arrays.asList(
                new Car(1L,1982, "MUE-2440", "GOL", "BRANCO"),
                new Car(2L, 1982, "HPP-1452", "GOL", "PRETO"),
                new Car(3L, 1990, "NBV-3535", "SIENA", "PRATA"),
                new Car(4L,2022, "IAP-2847", "ONIX", "BRANCO"),
                new Car(5L,1971, "EWT-9276", "FUSCA", "AMARELO"),
                new Car(6L,2019, "MQS-2478", "HB20", "BRANCO"),
                new Car(7L,1970, "MDQ-1937", "BRASILIA", "AMARELA")
        );
        carRepository.saveAll(cars);

        List<User> users = Arrays.asList(
                new User(1L,"Artur", "Silva", "artur@email.com",  new Date(93, 0, 14), "atcs", passwordEncoder.encode("h3ll0"), "81983351902",
                        Arrays.asList(new Car(1L, 1982, "MUE-2440", "GOL", "BRANCO"), new Car(2L, 1982, "HPP-1452", "GOL", "PRETO"))),

                new User(2L,"Maria", "Silva", "maria@email.com", new Date(93, 0, 14), "mcsf", passwordEncoder.encode("h3ll038475"), "81975843837", Arrays.asList(new Car(5L,1971, "EWT-9276", "FUSCA", "AMARELO")))
        );
        userRepository.saveAll(users);

    }
}
