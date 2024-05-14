package io.github.arturtcs.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name="TB_USER")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthday;

    private String login;
    private String password;
    private String phone;

//    @OneToMany
//    @JoinColumn(name = "id_car")
//    private Set<Car> carList = new HashSet<>();


}
