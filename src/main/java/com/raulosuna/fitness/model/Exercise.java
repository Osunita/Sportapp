package com.raulosuna.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exercises")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // “Press banca”, “Sentadilla”, etc.
    private Integer sets;       // Series
    private Integer reps;       // Repeticiones por serie
    private Double weight;      // Peso usado (kg), puede ser null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id")
    @JsonIgnore
    private Session session;
}