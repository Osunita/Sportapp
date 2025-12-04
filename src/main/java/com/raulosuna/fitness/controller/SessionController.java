package com.raulosuna.fitness.controller;

import com.raulosuna.fitness.model.Session;
import com.raulosuna.fitness.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    private String getCurrentUserEmail() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping
    public List<Session> getUserSessions() {
        return sessionService.getSessionsByEmail(getCurrentUserEmail());
    }

    @PostMapping
    public Session createSession(@RequestBody Session session) {
        return sessionService.createSession(session, getCurrentUserEmail());
    }

    @PutMapping("/{id}")
    public Session updateSession(@PathVariable Long id, @RequestBody Session session) {
        return sessionService.updateSession(id, session, getCurrentUserEmail());
    }

    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id, getCurrentUserEmail());
    }
/*
    @GetMapping
    public List<Session> getSessions() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return sessionService.getSessionsByUserEmail(email);
    }

    @PostMapping
    public Session createSession(@RequestBody Session sessionRequest) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return sessionService.createSession(email, sessionRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        sessionService.deleteSession(id, email);
    }

 */
}