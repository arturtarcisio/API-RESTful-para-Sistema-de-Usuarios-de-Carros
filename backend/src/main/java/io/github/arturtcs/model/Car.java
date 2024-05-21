package io.github.arturtcs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entity class representing a car.
 */
@Entity
@Data
@Table(name="TB_CAR")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int carYear;

    @Column(nullable = false, length = 12)
    @NotEmpty(message = "{field.licensePlate.required}")
    private String licensePlate;

    @Column(length = 25)
    private String model;

    @Column(length = 25)
    private String color;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="users")
    private User userOwner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(licensePlate, car.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(licensePlate);
    }
}
