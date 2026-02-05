package com.learners.learner_portal.service;

import com.learners.learner_portal.dto.ChatRequestDto;
import com.learners.learner_portal.dto.ChatResponseDto;

public interface ChatService {
    ChatResponseDto getChatResponse(ChatRequestDto request);
}