package com.microgames.mgwebsite.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.beans.factory.annotation.Autowired;

import com.microgames.mgwebsite.web.entities.Userclient;
import com.microgames.mgwebsite.web.repository.UserRepository;
import com.microgames.mgwebsite.web.dto.UserProfileDTO;


@Controller
public class ProfileController {

    private final UserRepository userRepository;

    // Inyección por constructor (recomendado)
    @Autowired
    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public String mostrarPerfil(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Userclient usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado " + username));

        // Convertir a DTO
        UserProfileDTO profile = new UserProfileDTO();
        profile.setId(usuario.getId());
        profile.setUsername(usuario.getUsername());
        profile.setEmail(usuario.getEmail());
        profile.setFirstname(usuario.getFirstname());
        profile.setLastname(usuario.getLastname());
        profile.setRol(usuario.getRol());

        model.addAttribute("profile", profile);

        return "mgwebsite/profile";   // Asegúrate que esta ruta de plantilla sea correcta
    }
}