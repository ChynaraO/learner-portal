package com.learners.learner_portal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learners.learner_portal.dto.UserRegistrationDto;
import com.learners.learner_portal.model.User;
import com.learners.learner_portal.service.JWTService;
import com.learners.learner_portal.service.UserService;
import com.learners.learner_portal.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .setControllerAdvice(new GlobalExceptionHandler()) // 🔥 REQUIRED
                .build();

        objectMapper = new ObjectMapper();
    }

    // ================= REGISTER =================

    @Test
    void register_Successful() throws Exception {
        UserRegistrationDto dto =
                new UserRegistrationDto("newUser", "password123");

        when(userService.registerUser(any(UserRegistrationDto.class)))
                .thenReturn(new User());

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated()) // ✅ FIXED
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    void register_UsernameAlreadyExists() throws Exception {
        UserRegistrationDto dto =
                new UserRegistrationDto("existingUser", "password123");

        doThrow(new IllegalArgumentException("Username already exists"))
                .when(userService).registerUser(any(UserRegistrationDto.class));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Username already exists"));
    }

    // ================= LOGIN =================

    @Test
    void login_Successful() throws Exception {
        UserRegistrationDto dto =
                new UserRegistrationDto("testUser", "password123");

        User user = new User();
        user.setUsername("testUser");
        user.setPasswordHash("encodedPassword");

        when(userService.findByUsername("testUser")).thenReturn(user);
        when(passwordEncoder.matches("password123", "encodedPassword"))
                .thenReturn(true);
        when(jwtService.generateToken(any(User.class)))
                .thenReturn("mock-token");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-token"));
    }

    @Test
    void login_IncorrectPassword() throws Exception {
        UserRegistrationDto dto =
                new UserRegistrationDto("testUser", "wrongPass");

        User user = new User();
        user.setUsername("testUser");
        user.setPasswordHash("encodedPassword");

        when(userService.findByUsername("testUser")).thenReturn(user);
        when(passwordEncoder.matches("wrongPass", "encodedPassword"))
                .thenReturn(false);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error")
                        .value("Invalid username or password"));
    }

    @Test
    void login_UserNotFound() throws Exception {
        UserRegistrationDto dto =
                new UserRegistrationDto("unknownUser", "password123");

        when(userService.findByUsername("unknownUser")).thenReturn(null);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error")
                        .value("Invalid username or password"));
    }
}
