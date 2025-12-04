package com.raulosuna.fitness.repository;

import com.raulosuna.fitness.model.Exercise;
import com.raulosuna.fitness.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findBySession(Session session);
}
