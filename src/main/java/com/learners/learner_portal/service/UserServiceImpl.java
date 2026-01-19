package com.learners.learner_portal.service;

import com.learners.learner_portal.model.User;

import com.learners.learner_portal.dto.UserLoginDto;
import com.learners.learner_portal.dto.UserRegistrationDto;
import com.learners.learner_portal.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

    @Service
    public class UserServiceImpl implements UserService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public UserServiceImpl(UserRepository userRepository,
                               PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

        // ✅ REGISTER
        @Override
        public User registerUser(UserRegistrationDto userData) {

            if (userRepository.findByUsername(userData.getUsername()) != null) {
                throw new IllegalArgumentException("Username already exists");
            }

            if (userRepository.findByEmail(userData.getEmail()) != null) {
                throw new IllegalArgumentException("Email already exists");
            }

            User user = new User();
            user.setUsername(userData.getUsername());
            user.setEmail(userData.getEmail());
            user.setPasswordHash(passwordEncoder.encode(userData.getPassword()));

            return userRepository.save(user);
        }

        // ✅ FIND BY USERNAME
        @Override
        public User findByUsername(String username) {
            return userRepository.findByUsername(username);
        }

        // ✅ FIND BY EMAIL
        @Override
        public User findByEmail(String email) {
            return userRepository.findByEmail(email);
        }

        // ✅ LOGIN
        @Override
        public boolean loginUser(UserLoginDto dto) {

            User user = userRepository.findByUsername(dto.getUsername());

            if (user == null) {
                return false;
            }

            return passwordEncoder.matches(
                    dto.getPassword(),
                    user.getPasswordHash()
            );
        }
    }
