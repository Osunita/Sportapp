package com.raulosuna.fitness.repository;

import com.raulosuna.fitness.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring genera automáticamente la query: SELECT * FROM users WHERE email = ?
    Optional<User> findByEmail(String email);

    // Para comprobar si ya existe un email al registrarse
    boolean existsByEmail(String email);
}
