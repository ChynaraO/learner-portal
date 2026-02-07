package com.learners.learner_portal.service;

import com.learners.learner_portal.dto.UserLoginDto;
import com.learners.learner_portal.dto.UserRegistrationDto;
import com.learners.learner_portal.model.User;

import java.util.Optional;

public interface UserService {

    User registerUser(UserRegistrationDto userData);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean checkPassword(String rawPassword, String encodedPassword);

    boolean loginUser(UserLoginDto dto);

    User loadUserByUsername(String username);
}