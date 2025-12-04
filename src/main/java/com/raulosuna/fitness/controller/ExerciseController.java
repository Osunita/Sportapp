package com.raulosuna.fitness.controller;

import com.raulosuna.fitness.model.Exercise;
import com.raulosuna.fitness.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions/{sessionId}/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    private String getEmail() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping
    public List<Exercise> list(@PathVariable Long sessionId) {
        return exerciseService.getExercises(sessionId, getEmail());
    }

    @PostMapping
    public Exercise create(@PathVariable Long sessionId, @RequestBody Exercise exercise) {
        return exerciseService.addExercise(sessionId, exercise, getEmail());
    }

    @PutMapping("/{exerciseId}")
    public Exercise update(@PathVariable Long exerciseId, @RequestBody Exercise exercise) {
        return exerciseService.updateExercise(exerciseId, exercise, getEmail());
    }

    @DeleteMapping("/{exerciseId}")
    public void delete(@PathVariable Long exerciseId) {
        exerciseService.deleteExercise(exerciseId, getEmail());
    }
}