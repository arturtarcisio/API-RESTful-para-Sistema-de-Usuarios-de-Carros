package io.github.arturtcs.service.impl;

import io.github.arturtcs.model.User;
import io.github.arturtcs.repository.UserRepository;
import io.github.arturtcs.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarService carService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setFirstName("Sivirino");
        user.setLastName("Fulano");
        user.setEmail("fulsiv@email.com");
        user.setPhone("12345678901");
        user.setLogin("fulanosiv");
        user.setPassword("minhasenha");
        user.setCars(new ArrayList<>());
    }

    @Test
    void testShowUsers() {
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.showUsers();
        assertThat(result).isEqualTo(users);

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testRegisterUser_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByLogin(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.registerUser(user);

        assertThat(result).isEqualTo(user);
        assertThat(result.getPassword()).isEqualTo("encodedPassword");

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).findByLogin(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.registerUser(user))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found");

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(0)).findByLogin(anyString());
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testRegisterUser_LoginAlreadyExists() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByLogin(anyString())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.registerUser(user))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Login already exists");

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).findByLogin(anyString());
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testFindUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Optional<User> result = userService.findUserById(1L);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);

        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).delete(any(User.class));
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.deleteUser(1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found");

        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(0)).delete(any(User.class));
    }

    @Test
    void testUpdateUser_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = new User();
        updatedUser.setFirstName("Sivirino");
        updatedUser.setLastName("Fulano");
        updatedUser.setEmail("ful.fiv@example.com");
        updatedUser.setPhone("10987654321");
        updatedUser.setLogin("fulanosiv");
        updatedUser.setPassword("minhasenha");
        updatedUser.setCars(new ArrayList<>());

        User result = userService.updateUser(1L, updatedUser);

        assertThat(result).isEqualTo(user);
        assertThat(result.getFirstName()).isEqualTo("Sivirino");
        assertThat(result.getPassword()).isEqualTo("encodedPassword");

        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        User updatedUser = new User();
        updatedUser.setFirstName("Fulano");
        updatedUser.setLastName("sivirino");
        updatedUser.setEmail("ful.fiv@example.com");
        updatedUser.setPhone("10987654321");
        updatedUser.setLogin("fulanosiv");
        updatedUser.setPassword("minhasenha");
        updatedUser.setCars(new ArrayList<>());

        assertThatThrownBy(() -> userService.updateUser(1L, updatedUser))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found");

        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(0)).save(any(User.class));
    }
}
