package com.learners.learner_portal.service;

import com.learners.learner_portal.dto.UserRegistrationDto;
import com.learners.learner_portal.model.User;

import java.util.Optional;

public interface UserService {
    User registerUser(UserRegistrationDto userData);
    User findByUsername(String username);
}
