package io.github.arturtcs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;

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

    @Column(nullable = false, length = 50)
    @NotEmpty(message = "{field.licensePlate.required}")
    private String licensePlate;

    @Column(nullable = false, length = 50)
    @NotEmpty(message = "{field.carModel.required}")
    private String model;

    private String color;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="users", nullable = false)
    private User userOwner;
}
