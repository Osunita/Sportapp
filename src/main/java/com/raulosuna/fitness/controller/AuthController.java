package com.raulosuna.fitness.controller;

import com.raulosuna.fitness.model.User;
import com.raulosuna.fitness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.raulosuna.fitness.model.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String token = userService.register(user);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest credentials) {
        String token = userService.login(credentials.getEmail(), credentials.getPassword());
        return ResponseEntity.ok(token);
    }
}
