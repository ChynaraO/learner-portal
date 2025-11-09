package com.learners.learner_portal.controller;

import com.learners.learner_portal.dto.ChatRequestDto;
import com.learners.learner_portal.dto.ChatResponseDto;
import com.learners.learner_portal.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    public ChatController(ChatService chatService){
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<?> processMessage(@Valid @RequestBody ChatRequestDto request) {
        try {
            ChatResponseDto response = chatService.processMessage(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
        //Handle validation errors gracefully
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String>
    handleValidationExceptions(MethodArgumentNotValidException ex){
        StringBuilder sb = new StringBuilder();
        for(FieldError error : ex.getBindingResult().getFieldErrors()) {
            sb.append((error.getField()))
                    .append(": ")
                    .append(error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(sb.toString());
        }
    }

