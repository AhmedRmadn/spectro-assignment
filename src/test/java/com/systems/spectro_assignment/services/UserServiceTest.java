package com.systems.spectro_assignment.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.systems.spectro_assignment.exceptions.CustomAuthenticationException;
import com.systems.spectro_assignment.models.Role;
import com.systems.spectro_assignment.models.User;
import com.systems.spectro_assignment.repositories.RoleRepository;
import com.systems.spectro_assignment.repositories.UserRepository;
import com.systems.spectro_assignment.requests.CreateUserRequest;
import com.systems.spectro_assignment.requests.LoginRequest;
import com.systems.spectro_assignment.responses.TokenResponse;
import com.systems.spectro_assignment.security.JwtUtil;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;
    
    

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test successful login when username exists and password matches.
     */
    @Test
    void userService_login_success() {
        User user = new User();
        user.setUsername("ahmed");
        user.setPassword("hashPass");

        when(userRepository.findByUsername("ahmed")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "hashPass")).thenReturn(true);
        when(jwtUtil.generateToken(user)).thenReturn("authToken");

        LoginRequest request = new LoginRequest();
        request.setUsername("ahmed");
        request.setPassword("123456");

        TokenResponse response = userService.login(request);
        assertThat(response.getToken()).isEqualTo("authToken");
    }

    /**
     * Test login failure when the user is not found.
     */
    @Test
    void userService_login_userNotFound() {
        when(userRepository.findByUsername("ahmed")).thenReturn(Optional.empty());

        LoginRequest request = new LoginRequest();
        request.setUsername("ahmed");
        request.setPassword("123456");

        CustomAuthenticationException ex = assertThrows(CustomAuthenticationException.class,
                () -> userService.login(request));

        assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * Test successful user creation with valid role and unique username.
     */
    @Test
    void userService_createUser_success() {
        Role role = new Role();
        role.setName("USER");

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("ahmed");
        request.setPassword("123456");
        request.setRoles(Set.of("USER"));

        when(userRepository.findByUsername("ahmed")).thenReturn(Optional.empty());
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("123456")).thenReturn("hashPass");
        when(jwtUtil.generateToken(any(User.class))).thenReturn("authToken");

        TokenResponse response = userService.createUser(request);
        assertThat(response.getToken()).isEqualTo("authToken");

        verify(userRepository).save(any(User.class));
    }

    /**
     * Test user creation failure when the username already exists.
     */
    @Test
    void userService_createUser_duplicateUsername() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("ahmed");
        request.setPassword("123456");
        request.setRoles(Set.of("USER"));

        when(userRepository.findByUsername("ahmed"))
                .thenReturn(Optional.of(new User()));

        CustomAuthenticationException ex = assertThrows(CustomAuthenticationException.class,
                () -> userService.createUser(request));

        assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.CONFLICT);
    }
}
