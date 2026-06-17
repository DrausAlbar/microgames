package com.microgames.mgwebsite.web.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "login_attempts")
public class LoginAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;                    // ← Usaremos solo esto

    @Column(nullable = false)
    private int attempts = 0;

    @Column(name = "lock_until")
    private LocalDateTime lockUntil;

    @Column(name = "last_attempt")
    private LocalDateTime lastAttempt = LocalDateTime.now();

    // Constructor vacío
    public LoginAttempt() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAttempts() { return attempts; }
    public void setAttempts(int attempts) { this.attempts = attempts; }

    public LocalDateTime getLockUntil() { return lockUntil; }
    public void setLockUntil(LocalDateTime lockUntil) { this.lockUntil = lockUntil; }

    public LocalDateTime getLastAttempt() { return lastAttempt; }
    public void setLastAttempt(LocalDateTime lastAttempt) { this.lastAttempt = lastAttempt; }

    // Métodos de negocio
    public boolean isLocked() {
        return lockUntil != null && LocalDateTime.now().isBefore(lockUntil);
    }

    public void incrementAttempts(int maxAttempts, int lockMinutes) {
        this.attempts++;
        this.lastAttempt = LocalDateTime.now();

        if (this.attempts >= maxAttempts) {
            this.lockUntil = LocalDateTime.now().plusMinutes(lockMinutes);
        }
    }

    public void reset() {
        this.attempts = 0;
        this.lockUntil = null;
        this.lastAttempt = LocalDateTime.now();
    }
}