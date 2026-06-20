package com.microgames.mgwebsite.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microgames.mgwebsite.web.entities.LoginAttempt;

public interface LoginAttemptRepository
        extends JpaRepository<LoginAttempt, Long> {

    Optional<LoginAttempt> findByUsernameIgnoreCase(String username);
}