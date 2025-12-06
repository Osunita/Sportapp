package com.raulosuna.fitness.controller;

import com.raulosuna.fitness.model.Exercise;
import com.raulosuna.fitness.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    private String getCurrentUserEmail() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping("/sessions/{sessionId}/exercises")
    public Exercise create(@PathVariable Long sessionId, @RequestBody Exercise exercise) {
        return exerciseService.addExercise(sessionId, exercise, getCurrentUserEmail());
    }

    @GetMapping("/sessions/{sessionId}/exercises")
    public List<Exercise> list(@PathVariable Long sessionId) {
        return exerciseService.getExercises(sessionId, getCurrentUserEmail());
    }

    @PutMapping("/exercises/{id}")
    public Exercise update(@PathVariable Long id, @RequestBody Exercise exercise) {
        return exerciseService.updateExercise(id, exercise, getCurrentUserEmail());
    }

    @DeleteMapping("/exercises/{id}")
    public void delete(@PathVariable Long id) {
        exerciseService.deleteExercise(id, getCurrentUserEmail());
    }
}
