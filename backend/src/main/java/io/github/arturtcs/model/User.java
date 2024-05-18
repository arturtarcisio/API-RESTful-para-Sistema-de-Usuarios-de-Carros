package io.github.arturtcs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Entity class representing a user.
 */
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
    @ToString.Exclude
    @OneToMany(mappedBy ="userOwner" , orphanRemoval = true, fetch=FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Car> cars = new ArrayList<>();

    @Column(nullable = true)
    private Instant createdAt;

    @Column(nullable = true)
    private Instant lastLogin;

    /**
     * Returns the authorities granted to the user.
     *
     * @return A collection of authorities.
     */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * @return The username.
     */
    @JsonIgnore
    @Override
    public String getUsername() {
        return this.login;
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return true if the user's account is valid (i.e., non-expired), false otherwise.
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     *
     * @return true if the user is not locked, false otherwise.
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired.
     *
     * @return true if the user's credentials are valid (i.e., non-expired), false otherwise.
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return true if the user is enabled, false otherwise.
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
