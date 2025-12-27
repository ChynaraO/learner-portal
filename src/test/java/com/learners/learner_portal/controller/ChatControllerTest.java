package com.learners.learner_portal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learners.learner_portal.dto.ChatRequestDto;
import com.learners.learner_portal.dto.ChatResponseDto;
import com.learners.learner_portal.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = ChatController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = com.learners.learner_portal.config.JWTAuthenticationFilter.class
        )
)
@AutoConfigureMockMvc(addFilters = false) // 🔥 THIS FIXES 403
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ChatService chatService;

    @Test
    void shouldReturn200_whenValidRequest() throws Exception {
        ChatRequestDto request = new ChatRequestDto("Hello AI");

        ChatResponseDto response =
                new ChatResponseDto("Hello human", "2025-01-01T10:00:00");

        when(chatService.processMessage(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Hello human"))
                .andExpect(jsonPath("$.timestamp").value("2025-01-01T10:00:00"));
    }

    @Test
    void shouldReturn400_whenMessageIsBlank() throws Exception {
        ChatRequestDto request = new ChatRequestDto("");

        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
