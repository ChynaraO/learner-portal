package com.learners.learner_portal.controller;

import com.learners.learner_portal.dto.UserLoginDto;
import com.learners.learner_portal.dto.UserRegistrationDto;
import com.learners.learner_portal.model.User;
import com.learners.learner_portal.service.JwtService;
import com.learners.learner_portal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthController(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    //Registration
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDto userData, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        try{
            userService. registerUser(userData);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    } //will show the rror message as a String
//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDto userData, BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            Map<String, List<String>> errors = new HashMap<>();
//
//            bindingResult.getFieldErrors().forEach(error -> {
//                errors.computeIfAbsent(error.getField(), key -> new ArrayList<>())
//                        .add(error.getDefaultMessage());
//            });
//            return ResponseEntity.badRequest().body(errors);
//        }
//        try{
//            userService. registerUser(userData);
//            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
//        } catch (IllegalArgumentException e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    } //will show the error message as a json
    //Login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody UserLoginDto loginData){
        User user = userService.findByUsername(loginData.getUsername());
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid username or password"));
        }
        boolean passwordMatches = passwordEncoder.matches(loginData.getPassword(), user.getPasswordHash());
        if(!passwordMatches){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid username or password"));
        }
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

}
