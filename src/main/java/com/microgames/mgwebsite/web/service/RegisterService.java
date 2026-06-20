package com.microgames.mgwebsite.web.service;

import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microgames.mgwebsite.web.dto.RegisterRequest;
import com.microgames.mgwebsite.web.entities.UserProfile;
import com.microgames.mgwebsite.web.entities.Usermain;
import com.microgames.mgwebsite.web.repository.UserProfileRepository;
import com.microgames.mgwebsite.web.repository.UsermainRepository;

@Service
public class RegisterService {

        private final UsermainRepository userRepository;
        private final UserProfileRepository profileRepository;
        private final PasswordEncoder passwordEncoder;

        private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9]{4,16}$");

        public RegisterService(
                        UsermainRepository userRepository,
                        UserProfileRepository profileRepository,
                        PasswordEncoder passwordEncoder) {

                this.userRepository = userRepository;
                this.profileRepository = profileRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @Transactional
        public void register(RegisterRequest request) {

                String username = request.getUsername().trim();
                String email = request.getEmail().trim().toLowerCase();

                // Validación: que no esté vacío ANTES de usar substring
                if (username.isBlank()) {
                        throw new IllegalArgumentException(
                                        "El usuario no puede estar vacío");
                }

                // Primera letra mayúscula (ahora es seguro, ya sabemos que username no está
                // vacío)
                username = username.substring(0, 1).toUpperCase()
                                + username.substring(1);

                // Validación username
                if (!USERNAME_PATTERN.matcher(username).matches()) {
                        throw new IllegalArgumentException(
                                        "El usuario debe tener entre 4 y 16 caracteres alfanuméricos");
                }

                // Validación email
                if (email.isBlank()) {
                        throw new IllegalArgumentException(
                                        "Email inválido");
                }

                // Validación password
                if (request.getPassword().length() < 8
                                || request.getPassword().length() > 64) {

                        throw new IllegalArgumentException(
                                        "La contraseña debe tener entre 8 y 64 caracteres");
                }

                // Confirmación password
                if (!request.getPassword()
                                .equals(request.getConfirmPassword())) {

                        throw new IllegalArgumentException(
                                        "Las contraseñas no coinciden");
                }

                // Validación pregunta secreta
                if (request.getSecretQuestion() == null
                                || request.getSecretQuestion().isBlank()) {

                        throw new IllegalArgumentException(
                                        "Debes ingresar una pregunta secreta");
                }

                // Validación respuesta secreta
                if (request.getSecretAnswer() == null
                                || request.getSecretAnswer().isBlank()) {

                        throw new IllegalArgumentException(
                                        "Debes ingresar una respuesta secreta");
                }

                // Username duplicado
                if (userRepository.findByUsername(username).isPresent()) {

                        throw new IllegalArgumentException(
                                        "Ese nombre de usuario ya existe");
                }

                // Email duplicado
                if (userRepository.findByEmail(email).isPresent()) {

                        throw new IllegalArgumentException(
                                        "Ese email ya está registrado");
                }

                // Crear usuario
                Usermain user = new Usermain();

                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(
                                passwordEncoder.encode(request.getPassword()));

                user.setSecretQuestion(
                                request.getSecretQuestion().trim());

                // La respuesta se normaliza (minúsculas, sin espacios extra) antes de hashear,
                // para que la verificación luego no falle por mayúsculas o espacios.
                String normalizedAnswer = request.getSecretAnswer().trim().toLowerCase();

                user.setSecretAnswerHash(
                                passwordEncoder.encode(normalizedAnswer));

                // Crear perfil
                UserProfile profile = new UserProfile();

                profile.setUsermain(user);

                user.setProfile(profile);

                // Guardar
                userRepository.save(user);
        }
}