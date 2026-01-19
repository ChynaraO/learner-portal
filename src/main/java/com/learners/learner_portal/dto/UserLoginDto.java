package com.learners.learner_portal.dto;

import jakarta.validation.constraints.NotBlank;

public class UserLoginDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public UserLoginDto() {
    }

    public UserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
