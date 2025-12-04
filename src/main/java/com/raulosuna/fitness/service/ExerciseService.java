package com.raulosuna.fitness.service;

import com.raulosuna.fitness.model.Exercise;
import com.raulosuna.fitness.model.Session;
import com.raulosuna.fitness.model.User;
import com.raulosuna.fitness.repository.ExerciseRepository;
import com.raulosuna.fitness.repository.SessionRepository;
import com.raulosuna.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    private Session getValidatedSession(Long sessionId, String userEmail) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));

        if (!session.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("No autorizado");
        }

        return session;
    }

    public List<Exercise> getExercises(Long sessionId, String userEmail) {
        Session session = getValidatedSession(sessionId, userEmail);
        return exerciseRepository.findBySession(session);
    }

    public Exercise addExercise(Long sessionId, Exercise exercise, String userEmail) {
        Session session = getValidatedSession(sessionId, userEmail);
        exercise.setSession(session);
        return exerciseRepository.save(exercise);
    }

    public Exercise updateExercise(Long exerciseId, Exercise updated, String userEmail) {
        Exercise existing = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado"));

        if (!existing.getSession().getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("No autorizado");
        }

        existing.setName(updated.getName());
        existing.setSets(updated.getSets());
        existing.setReps(updated.getReps());
        existing.setWeight(updated.getWeight());

        return exerciseRepository.save(existing);
    }

    public void deleteExercise(Long exerciseId, String userEmail) {
        Exercise existing = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado"));

        if (!existing.getSession().getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("No autorizado");
        }

        exerciseRepository.delete(existing);
    }
}
