package com.learners.learner_portal.dto;

public class UserRegistrationDto {
    private String username;
    private String password;
    //constructor
    public UserRegistrationDto(String username, String password){
        this.username = username;
        this.password = password;
    }
    //Getters
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
}
