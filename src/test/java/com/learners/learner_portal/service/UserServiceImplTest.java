//package com.learners.learner_portal.service;
//
//import com.learners.learner_portal.dto.UserRegistrationDto;
//import com.learners.learner_portal.model.User;
//import com.learners.learner_portal.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class) // enables Mockito in JUnit 5
//class UserServiceImplTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    private UserRegistrationDto userDto;
//
//    @BeforeEach
//    void setUp() {
//        userDto = new UserRegistrationDto();
//        userDto.setUsername("testUser");
//        userDto.setPassword("password123");
//        userDto.setEmail("test@test.com");
//    }
//
//
//    @Test
//    void registerUser_SuccessfulRegistration() {
//        // Arrange
//        when(userRepository.findByUsername("testUser")).thenReturn(null);
//        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
//
//        User savedUser = new User();
//        savedUser.setUsername("testUser");
//        savedUser.setPasswordHash("encodedPassword");
//        when(userRepository.save(any(User.class))).thenReturn(savedUser);
//
//        // Act
//        User result = userService.registerUser(userDto);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals("testUser", result.getUsername());
//        assertEquals("encodedPassword", result.getPasswordHash());
//
//        verify(userRepository).findByUsername("testUser");
//        verify(passwordEncoder).encode("password123");
//        verify(userRepository).save(any(User.class));
//    }
//
//    @Test
//    void registerUser_UsernameAlreadyExists_ThrowsException() {
//        // Arrange
//        User existingUser = new User();
//        existingUser.setUsername("testUser");
//        when(userRepository.findByUsername("testUser")).thenReturn(existingUser);
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(userDto));
//
//        verify(userRepository).findByUsername("testUser");
//        verify(userRepository, never()).save(any(User.class));
//    }
//
//    @Test
//    void findByUsername_UserExists_ReturnUser() {
//        // Arrange
//        User user = new User();
//        user.setUsername("testUser");
//        when(userRepository.findByUsername("testUser")).thenReturn(user);
//
//        // Act
//        Optional <User> result = Optional.ofNullable(userService.findByUsername("testUser"));
//
//        // Assert
//        assertTrue(result.isPresent());
//        assertEquals("testUser", result.get().getUsername());
//        verify(userRepository).findByUsername("testUser");
//    }
//
//    @Test
//    void findByUsername_UserNotFound_ReturnsEmptyOptional() {
//        // Arrange
//        when(userRepository.findByUsername("unknown")).thenReturn(null);
//
//        // Act
//        User result = userService.findByUsername("unknown");
//
//        // Assert
//        assertNull(result);
//        verify(userRepository).findByUsername("unknown");
//    }
//}
