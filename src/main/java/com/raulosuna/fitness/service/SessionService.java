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

    public List<Session> getSessionsByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return sessionRepository.findByUser(user);
    }

    public Session createSession(Session session, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        session.setUser(user);
        return sessionRepository.save(session);
    }

    public Session updateSession(Long id, Session updatedSession, String email) {
        Session existing = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));

        if (!existing.getUser().getEmail().equals(email)) {
            throw new RuntimeException("No autorizado");
        }

        existing.setName(updatedSession.getName());
        existing.setDateTime(updatedSession.getDateTime());
        existing.setDurationMinutes(updatedSession.getDurationMinutes());
        existing.setComment(updatedSession.getComment());

        return sessionRepository.save(existing);
    }

    public void deleteSession(Long id, String email) {
        Session existing = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));

        if (!existing.getUser().getEmail().equals(email)) {
            throw new RuntimeException("No autorizado");
        }

        sessionRepository.delete(existing);
    }
}