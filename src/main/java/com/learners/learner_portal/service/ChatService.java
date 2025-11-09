package com.learners.learner_portal.service;

import com.learners.learner_portal.dto.ChatRequestDto;
import com.learners.learner_portal.dto.ChatResponseDto;

public interface ChatService {
    /**
     * Process a chat message and return a response
     * @param request The chat request containing the user's message
     * @return ChatResponseDto containing the AI's response and timestamp
     */
    ChatResponseDto processMessage(ChatRequestDto request);
}
