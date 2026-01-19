package com.learners.learner_portal.service;

import com.learners.learner_portal.dto.UserLoginDto;
import com.learners.learner_portal.dto.UserRegistrationDto;
import com.learners.learner_portal.model.User;

public interface UserService {

    User registerUser(UserRegistrationDto userData);

    User findByUsername(String username);

    User findByEmail(String email);   // ✅ ADD THIS

    boolean loginUser(UserLoginDto dto);
}
