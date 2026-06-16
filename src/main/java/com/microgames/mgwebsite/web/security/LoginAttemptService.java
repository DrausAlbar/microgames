package com.microgames.mgwebsite.web.security;

import com.microgames.mgwebsite.web.entities.LoginAttempt;
import com.microgames.mgwebsite.web.repository.LoginAttemptRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class LoginAttemptService {

    private final LoginAttemptRepository loginAttemptRepository;

    private static final int MAX_ATTEMPTS = 3   ;
    private static final int LOCK_MINUTES = 15;

    public LoginAttemptService(LoginAttemptRepository loginAttemptRepository) {
        this.loginAttemptRepository = loginAttemptRepository;
    }

    @Transactional
    public void loginFailed(String username) {
        String key = username.toLowerCase().trim();

        LoginAttempt attempt = loginAttemptRepository.findByUsername(key)
                .orElseGet(() -> {
                    LoginAttempt newAttempt = new LoginAttempt();
                    newAttempt.setUsername(key);
                    return newAttempt;
                });

        attempt.incrementAttempts(MAX_ATTEMPTS, LOCK_MINUTES);
        loginAttemptRepository.save(attempt);
    }

    @Transactional
    public void loginSucceeded(String username) {
        String key = username.toLowerCase().trim();
        loginAttemptRepository.findByUsername(key)
                .ifPresent(attempt -> {
                    attempt.reset();
                    loginAttemptRepository.save(attempt);
                });
    }

    @Transactional(readOnly = true)
    public boolean isBlocked(String username) {
        String key = username.toLowerCase().trim();
        return loginAttemptRepository.findByUsername(key)
                .map(LoginAttempt::isLocked)
                .orElse(false);
    }
}