package com.learners.learner_portal.service;

import com.learners.learner_portal.dto.ChatRequestDto;
import com.learners.learner_portal.dto.ChatResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ChatServiceImpl implements ChatService{
    @Override
    public ChatResponseDto processMessage(ChatRequestDto request){
        String response = "Echo: " + request.getMessage();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        return  new ChatResponseDto(response, timestamp);
    }
}
