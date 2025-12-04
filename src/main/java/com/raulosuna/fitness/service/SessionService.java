package com.raulosuna.fitness.service;

import com.raulosuna.fitness.model.Session;
import com.raulosuna.fitness.model.User;
import com.raulosuna.fitness.repository.SessionRepository;
import com.raulosuna.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public Session createSession(String userEmail, Session session) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        session.setUser(user);
        return sessionRepository.save(session);
    }

    public List<Session> getSessionsByUserEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return sessionRepository.findByUser(user);
    }

    public void deleteSession(Long sessionId, String userEmail) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session no encontrada"));
        if (!session.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("No autorizado");
        }
        sessionRepository.delete(session);
    }
}