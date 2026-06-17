package com.microgames.mgwebsite.web.security;

import com.microgames.mgwebsite.web.entities.LoginAttempt;
import com.microgames.mgwebsite.web.repository.LoginAttemptRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginAttemptService {

    private final LoginAttemptRepository loginAttemptRepository;

    private static final int MAX_ATTEMPTS = 3;
    private static final int LOCK_MINUTES = 15;

    public LoginAttemptService(LoginAttemptRepository loginAttemptRepository) {
        this.loginAttemptRepository = loginAttemptRepository;
    }

    @Transactional
    public void loginFailed(String email) {
        String key = email.toLowerCase().trim();
        System.out.println("Login failed for email: " + key); // Debugging line

        LoginAttempt attempt = loginAttemptRepository.findByEmail(key)
                .orElseGet(() -> {
                    LoginAttempt newAttempt = new LoginAttempt();
                    newAttempt.setEmail(key);
                    return newAttempt;
                });

        attempt.incrementAttempts(MAX_ATTEMPTS, LOCK_MINUTES);
        loginAttemptRepository.save(attempt);
    }

    @Transactional
    public void loginSucceeded(String email) {
        String key = email.toLowerCase().trim();
        loginAttemptRepository.findByEmail(key)
                .ifPresent(attempt -> {
                    attempt.reset();
                    loginAttemptRepository.save(attempt);
                });
    }

    @Transactional(readOnly = true)
    public boolean isBlocked(String email) {
        String key = email.toLowerCase().trim();
        return loginAttemptRepository.findByEmail(key)
                .map(LoginAttempt::isLocked)
                .orElse(false);
    }
}