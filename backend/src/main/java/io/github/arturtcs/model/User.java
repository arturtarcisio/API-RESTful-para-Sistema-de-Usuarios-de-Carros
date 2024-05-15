package io.github.arturtcs.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="TB_USERS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    @NotEmpty(message = "{field.firstName.required}")
    private String firstName;

    private String lastName;

    @Column(nullable = false, length = 150)
    @NotEmpty(message = "{field.email.required}")
    private String email;

    private Date birthday;

    @Column(nullable = false, length = 150)
    @NotEmpty(message = "{field.login.required}")
    private String login;

    @Column(nullable = false, length = 150)
    @NotEmpty(message = "{field.password.required}")
    private String password;

    private String phone;

    @ManyToMany
    @JoinTable(
            name = "tb_user_cars",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private List<Car> cars = new ArrayList<>();

}
