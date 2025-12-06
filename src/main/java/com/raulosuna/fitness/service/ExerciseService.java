package com.raulosuna.fitness.service;

import com.raulosuna.fitness.model.Exercise;
import com.raulosuna.fitness.model.Session;
import com.raulosuna.fitness.repository.ExerciseRepository;
import com.raulosuna.fitness.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final SessionRepository sessionRepository;
    private final ExerciseRepository exerciseRepository;

    // Verifica que la sesión exista y pertenece al email
    private Session getValidatedSession(Long sessionId, String email) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session no encontrada"));

        if (!session.getUser().getEmail().equals(email)) {
            throw new RuntimeException("No tienes permiso para esta sesión");
        }

        return session;
    }

    // Crear ejercicio
    public Exercise addExercise(Long sessionId, Exercise exercise, String email) {
        Session session = getValidatedSession(sessionId, email);
        exercise.setSession(session);
        return exerciseRepository.save(exercise);
    }

    // Obtener ejercicios de una sesión
    public List<Exercise> getExercises(Long sessionId, String email) {
        Session session = getValidatedSession(sessionId, email);
        return session.getExercises(); // ya está en memoria
    }

    // Editar ejercicio
    public Exercise updateExercise(Long exerciseId, Exercise updated, String email) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise no encontrado"));

        // Validar owner
        if (!exercise.getSession().getUser().getEmail().equals(email)) {
            throw new RuntimeException("No tienes permiso para editar este ejercicio");
        }

        exercise.setName(updated.getName());
        exercise.setSets(updated.getSets());
        exercise.setReps(updated.getReps());
        exercise.setWeight(updated.getWeight());

        return exerciseRepository.save(exercise);
    }

    // Eliminar ejercicio
    public void deleteExercise(Long exerciseId, String email) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise no encontrado"));

        if (!exercise.getSession().getUser().getEmail().equals(email)) {
            throw new RuntimeException("No tienes permiso para eliminar este ejercicio");
        }

        exerciseRepository.delete(exercise);
    }
}
