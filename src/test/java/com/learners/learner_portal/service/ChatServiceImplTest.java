package com.learners.learner_portal.service;

import com.learners.learner_portal.dto.ChatRequestDto;
import com.learners.learner_portal.dto.ChatResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

    class ChatServiceImplTest {

        private ChatService chatService;

        @BeforeEach
        void setUp() {
            chatService = new ChatServiceImpl();
        }

        @Test
        void processMessage_shouldReturnEchoResponse() {
            // Arrange
            ChatRequestDto request = new ChatRequestDto();
            request.setMessage("Hello AI");

            // Act
            ChatResponseDto response = chatService.processMessage(request);

            // Assert
            assertNotNull(response);
            assertEquals("Echo: Hello AI", response.getResponse());
            assertNotNull(response.getTimestamp());
            assertFalse(response.getTimestamp().isEmpty());
        }

        @Test
        void processMessage_shouldHandleEmptyMessage() {
            // Arrange
            ChatRequestDto request = new ChatRequestDto();
            request.setMessage("");

            // Act
            ChatResponseDto response = chatService.processMessage(request);

            // Assert
            assertNotNull(response);
            assertEquals("Echo: ", response.getResponse());
            assertNotNull(response.getTimestamp());
        }
    }

