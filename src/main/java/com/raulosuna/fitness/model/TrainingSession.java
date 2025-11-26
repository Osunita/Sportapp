package com.raulosuna.fitness.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "training_sessions")
@Data
public class TrainingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Relación con User

    @Column(nullable = false)
    private LocalDateTime date = LocalDateTime.now();  // Default ahora

    @Column(nullable = false)
    private String sportType;  // Ej. "running", "gym"

    @Column(nullable = false)
    private int duration;  // Minutos

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Intensity intensity;  // Enum abajo

    private String notes;  // Opcional
}

// Enum para Intensity (crea en mismo file o separado)
enum Intensity {
    LOW, MEDIUM, HIGH
}