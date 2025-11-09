package com.learners.learner_portal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class APIKeyProvider {

    @Value("${api.gemini.api-key}")
    private String geminiApiKey;

    public String getGeminiApiKey(){
        return geminiApiKey;
    }
}
