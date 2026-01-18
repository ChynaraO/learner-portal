package com.learners.learner_portal.controller;

import com.learners.learner_portal.dto.UserLoginDto;
import com.learners.learner_portal.dto.UserRegistrationDto;
import com.learners.learner_portal.model.User;
import com.learners.learner_portal.service.JWTService;
import com.learners.learner_portal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public AuthController(
            UserService userService,
            PasswordEncoder passwordEncoder,
            JWTService jwtService
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @Valid @RequestBody UserRegistrationDto userData,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errorMessage);
        }

        userService.registerUser(userData);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User regised successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody UserLoginDto loginData
    ) {
        User user = userService.findByUsername(loginData.getUsername());

        if (user == null ||
                !passwordEncoder.matches(
                        loginData.getPassword(),
                        user.getPasswordHash())
        ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }

        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
