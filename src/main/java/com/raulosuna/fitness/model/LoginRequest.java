package com.raulosuna.fitness.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}