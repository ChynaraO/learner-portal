package com.learners.learner_portal.controller;

import com.learners.learner_portal.dto.ChatRequestDto;
import com.learners.learner_portal.dto.ChatResponseDto;
import com.learners.learner_portal.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    public ChatController(ChatService chatService){
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ChatResponseDto> processMessage(@RequestBody ChatRequestDto request){
        ChatResponseDto response = chatService.processMessage(request);
        return ResponseEntity.ok(response);
    }

}
