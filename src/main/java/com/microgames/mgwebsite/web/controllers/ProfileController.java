package com.microgames.mgwebsite.web.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.microgames.mgwebsite.web.dto.UserProfileDTO;
import com.microgames.mgwebsite.web.entities.UserProfile;
import com.microgames.mgwebsite.web.entities.Usermain;
import com.microgames.mgwebsite.web.repository.UserProfileRepository;
import com.microgames.mgwebsite.web.repository.UsermainRepository;

@Controller
public class ProfileController {

    private final UsermainRepository userRepository;
    private final UserProfileRepository profileRepository;

    public ProfileController(
            UsermainRepository userRepository,
            UserProfileRepository profileRepository) {

        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    @GetMapping("/profile")
    public String mostrarPerfil(Model model) {

        Usermain usuario = usuarioActual();

        UserProfile perfil = profileRepository
                .findByUsermain(usuario)
                .orElseGet(() -> {

                    UserProfile nuevo = new UserProfile();
                    nuevo.setUsermain(usuario);

                    return nuevo;
                });

        UserProfileDTO dto = new UserProfileDTO();

        dto.setUsername(usuario.getUsername());
        dto.setEmail(usuario.getEmail());
        dto.setFirstName(perfil.getFirstName());
        dto.setLastName(perfil.getLastName());

        model.addAttribute("profileForm", dto);

        return "mgwebsite/profile";
    }

    @PostMapping("/profile")
    public String actualizarPerfil(
            @ModelAttribute UserProfileDTO profileForm,
            Model model) {

        Usermain usuario = usuarioActual();

        UserProfile perfil = profileRepository
                .findByUsermain(usuario)
                .orElseGet(() -> {

                    UserProfile nuevo = new UserProfile();
                    nuevo.setUsermain(usuario);

                    return nuevo;
                });

        perfil.setFirstName(profileForm.getFirstName());
        perfil.setLastName(profileForm.getLastName());

        profileRepository.save(perfil);

        return "redirect:/profile";
    }

    private Usermain usuarioActual() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        return userRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado: " + username));
    }
}