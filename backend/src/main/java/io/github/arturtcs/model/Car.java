package io.github.arturtcs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@Table(name="TB_CAR")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int carYear;
    private String licensePlate;
    private String model;
    private String color;

}
