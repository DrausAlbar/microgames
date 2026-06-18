package com.microgames.mgwebsite.web.security;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microgames.mgwebsite.web.entities.LoginAttempt;
import com.microgames.mgwebsite.web.repository.LoginAttemptRepository;

@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 3;
    private static final int LOCK_MINUTES = 15;

    private final LoginAttemptRepository repository;

    public LoginAttemptService(LoginAttemptRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void loginFailed(String username) {
//DEBUG
    System.out.println("loginFailed ejecutado: " + username);

        if (username == null || username.isBlank()) {
            return;
        }

        LoginAttempt attempt = repository
                .findByUsernameIgnoreCase(username)
                .orElseGet(() -> {

                    LoginAttempt newAttempt =
                            new LoginAttempt();

                    newAttempt.setUsername(username);

                    return newAttempt;
                });

        attempt.setFailedAttempts(
                attempt.getFailedAttempts() + 1);

        attempt.setLastAttempt(
                LocalDateTime.now());

        if (attempt.getFailedAttempts()
                >= MAX_ATTEMPTS) {

            attempt.setLockedUntil(
                    LocalDateTime.now()
                            .plusMinutes(LOCK_MINUTES));
        }

        repository.save(attempt);
    }

    @Transactional
    public void loginSucceeded(String username) {

        repository
                .findByUsernameIgnoreCase(username)
                .ifPresent(attempt -> {

                    attempt.setFailedAttempts(0);
                    attempt.setLockedUntil(null);

                    repository.save(attempt);
                });
    }

    public boolean isBlocked(String username) {

        return repository
                .findByUsernameIgnoreCase(username)
                .map(attempt -> {

                    LocalDateTime lockedUntil =
                            attempt.getLockedUntil();

                    if (lockedUntil == null) {
                        return false;
                    }

                    if (lockedUntil.isBefore(
                            LocalDateTime.now())) {

                        attempt.setFailedAttempts(0);
                        attempt.setLockedUntil(null);

                        repository.save(attempt);

                        return false;
                    }

                    return true;

                })
                .orElse(false);
    }
}