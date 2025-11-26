package com.raulosuna.fitness.repository;

import com.raulosuna.fitness.model.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {

    // Todas las sesiones de un usuario ordenadas por fecha descendente
    List<TrainingSession> findByUserIdOrderByDateDesc(Long userId);

    // Para el recordatorio "cuánto hace que no entrenas"
    TrainingSession findTopByUserIdOrderByDateDesc(Long userId);

    // Para los gráficos: suma de minutos por semana del usuario actual
    @Query("SELECT SUM(s.duration) FROM TrainingSession s WHERE s.user.id = :userId " +
            "AND s.date BETWEEN :start AND :end")
    Integer sumDurationByUserAndDateBetween(Long userId, LocalDateTime start, LocalDateTime end);

    // Conteo de sesiones por tipo de deporte (para pie-chart)
    @Query("SELECT s.sportType, COUNT(s) FROM TrainingSession s WHERE s.user.id = :userId GROUP BY s.sportType")
    List<Object[]> countBySportTypeGrouped(Long userId);
}