package com.microgames.mgwebsite.web.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microgames.mgwebsite.web.entities.Usermain;
import com.microgames.mgwebsite.web.repository.UsermainRepository;

@Service
public class ResetPasswordService {

    private static final int MAX_ATTEMPTS = 3;
    private static final int LOCK_MINUTES = 15;

    private final UsermainRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResetPasswordService(
            UsermainRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

public String obtenerPreguntaSecreta(String username, String email) {

    Usermain user = userRepository
            .findByUsernameIgnoreCase(username)
            .orElse(null);

    if (user == null
            || email == null
            || !email.trim().equalsIgnoreCase(user.getEmail())) {

        throw new IllegalArgumentException(
                "Los datos no coinciden");
    }

    if (estaBloqueado(user)) {
        throw new IllegalArgumentException(
                "Demasiados intentos fallidos. Inténtalo de nuevo en unos minutos.");
    }

    if (user.getSecretQuestion() == null
            || user.getSecretQuestion().isBlank()) {

        throw new IllegalArgumentException(
                "Este usuario no tiene una pregunta secreta configurada");
    }

    return user.getSecretQuestion();
}


public String obtenerPreguntaSecretaSinValidarEmail(String username) {

    Usermain user = userRepository
            .findByUsernameIgnoreCase(username)
            .orElseThrow(() -> new IllegalArgumentException(
                    "Los datos no coinciden"));

    if (estaBloqueado(user)) {
        throw new IllegalArgumentException(
                "Demasiados intentos fallidos. Inténtalo de nuevo en unos minutos.");
    }

    if (user.getSecretQuestion() == null
            || user.getSecretQuestion().isBlank()) {

        throw new IllegalArgumentException(
                "Este usuario no tiene una pregunta secreta configurada");
    }

    return user.getSecretQuestion();
}

    public void confirmarReseteo(
            String username,
            String secretAnswer,
            String newPassword,
            String confirmPassword) {

        Usermain user = userRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuario no encontrado"));

        if (estaBloqueado(user)) {
            throw new IllegalArgumentException(
                    "Demasiados intentos fallidos. Inténtalo de nuevo en unos minutos.");
        }

        boolean respuestaCorrecta = secretAnswer != null
                && passwordEncoder.matches(
                        secretAnswer.trim().toLowerCase(),
                        user.getSecretAnswerHash());

        if (!respuestaCorrecta) {

            user.setFailedPasswordAttempts(
                    user.getFailedPasswordAttempts() + 1);

            user.setLastPasswordAttemptDate(
                    LocalDateTime.now());

            boolean alcanzoElMaximo = user.getFailedPasswordAttempts() >= MAX_ATTEMPTS;

            if (alcanzoElMaximo) {

                user.setPasswordLockedUntil(
                        LocalDateTime.now().plusMinutes(LOCK_MINUTES));
            }

            userRepository.save(user);

            if (alcanzoElMaximo) {

                // Mensaje especial: el Controller lo reconoce y redirige a /login
                throw new IllegalArgumentException("BLOQUEADO");
            }

            throw new IllegalArgumentException(
                    "La respuesta secreta es incorrecta");

        }
        if (newPassword == null
                || newPassword.length() < 8
                || newPassword.length() > 64) {

            throw new IllegalArgumentException(
                    "La contraseña debe tener entre 8 y 64 caracteres");
        }

        if (!newPassword.equals(confirmPassword)) {

            throw new IllegalArgumentException(
                    "Las contraseñas no coinciden");
        }

        user.setFailedPasswordAttempts(0);
        user.setPasswordLockedUntil(null);

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    private boolean estaBloqueado(Usermain user) {

        LocalDateTime lockedUntil = user.getPasswordLockedUntil();

        if (lockedUntil == null) {
            return false;
        }

        if (lockedUntil.isBefore(LocalDateTime.now())) {

            user.setFailedPasswordAttempts(0);
            user.setPasswordLockedUntil(null);
            userRepository.save(user);

            return false;
        }

        return true;
    }
}