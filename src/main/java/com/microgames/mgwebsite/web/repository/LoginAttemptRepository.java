package com.microgames.mgwebsite.web.repository;

import com.microgames.mgwebsite.web.entities.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {
    
    Optional<LoginAttempt> findByUsername(String username);
}