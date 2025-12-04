package com.raulosuna.fitness.repository;

import com.raulosuna.fitness.model.Session;
import com.raulosuna.fitness.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByUser(User user);
}