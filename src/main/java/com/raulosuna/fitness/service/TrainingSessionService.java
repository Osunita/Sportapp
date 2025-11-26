package com.raulosuna.fitness.service;

import com.raulosuna.fitness.model.TrainingSession;
import com.raulosuna.fitness.repository.TrainingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TrainingSessionService {

    @Autowired
    private TrainingSessionRepository sessionRepository;

    public TrainingSession create(TrainingSession session, Long userId) {
        // Set user desde auth después
        return sessionRepository.save(session);
    }

    public List<TrainingSession> getByUser(Long userId) {
        return sessionRepository.findByUserIdOrderByDateDesc(userId);
    }

    // Similar para update/delete con check userId

    public int getWeeklyDuration(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.with(ChronoField.DAY_OF_WEEK, 1);
        Integer sum = sessionRepository.sumDurationByUserAndDateBetween(userId, start, now);
        return sum == null ? 0 : sum;
    }

    public Map<String, Long> getSportCounts(Long userId) {
        return sessionRepository.countBySportTypeGrouped(userId).stream()
                .collect(Collectors.toMap(obj -> (String) obj[0], obj -> (Long) obj[1]));
    }

    public String getReminder(Long userId) {
        TrainingSession last = sessionRepository.findTopByUserIdOrderByDateDesc(userId);
        if (last == null) return "¡Empieza a entrenar!";
        long days = java.time.Duration.between(last.getDate(), LocalDateTime.now()).toDays();
        if (days > 3) return "Han pasado " + days + " días sin entrenar!";
        return "";
    }
}
