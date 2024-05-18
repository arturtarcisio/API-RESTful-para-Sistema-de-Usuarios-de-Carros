package io.github.arturtcs.model;

import io.github.arturtcs.model.dto.LoginRequestDTO;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="TB_USERS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @NotEmpty(message = "{field.firstName.required}")
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(nullable = false, length = 50)
    @NotEmpty(message = "{field.email.required}")
    private String email;

    private Date birthday;

    @Column(nullable = false, length = 50)
    @NotEmpty(message = "{field.login.required}")
    private String login;

    @Column(nullable = false, length = 150)
    @NotEmpty(message = "{field.password.required}")
    private String password;

    private String phone;

    @Nullable
    @OneToMany(mappedBy ="userOwner" , orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Car> cars = new ArrayList<>();

    @Column(nullable = true)
    private Instant createdAt;

    @Column(nullable = true)
    private Instant lastLogin;

    public boolean isLoginCorrect(LoginRequestDTO loginRequestDTO, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequestDTO.password(), this.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
