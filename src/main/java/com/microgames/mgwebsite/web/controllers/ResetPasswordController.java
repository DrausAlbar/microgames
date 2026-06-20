package com.microgames.mgwebsite.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.microgames.mgwebsite.web.service.ResetPasswordService;

@Controller
public class ResetPasswordController {

    private final ResetPasswordService resetPasswordService;

    public ResetPasswordController(ResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
    }

    // Pantalla 1: pedir el username
    @GetMapping("/reset-password")
    public String resetPasswordPage() {
        return "mgwebsite/reset-password";
    }

    // Procesa el username, busca la pregunta secreta y muestra la pantalla 2
    @PostMapping("/reset-password/verify")
    public String verifyUsername(
            @RequestParam String username,
            Model model) {

        try {

            String secretQuestion =
                    resetPasswordService.obtenerPreguntaSecreta(username);

            model.addAttribute("username", username);
            model.addAttribute("secretQuestion", secretQuestion);

            return "mgwebsite/reset-password-answer";

        } catch (IllegalArgumentException ex) {

            model.addAttribute("error", ex.getMessage());

            return "mgwebsite/reset-password";
        }
    }

    // Pantalla 2: verifica la respuesta y cambia la contraseña
    @PostMapping("/reset-password/confirm")
    public String confirmReset(
            @RequestParam String username,
            @RequestParam String secretAnswer,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Model model) {

        try {

            resetPasswordService.confirmarReseteo(
                    username, secretAnswer, newPassword, confirmPassword);

            return "redirect:/login?reset";

        } catch (IllegalArgumentException ex) {

            // Si se alcanzó el máximo de intentos, mandamos directo a /login
            // con un aviso, igual que hace el BruteForceFilter del login normal.
            if ("BLOQUEADO".equals(ex.getMessage())) {

                return "redirect:/login?locked";
            }

            // Si falla por otra razón, volvemos a mostrar la pantalla 2 con el error
            // (recuperamos la pregunta de nuevo para no perderla)
            try {

                String secretQuestion =
                        resetPasswordService.obtenerPreguntaSecreta(username);

                model.addAttribute("secretQuestion", secretQuestion);

            } catch (IllegalArgumentException ignored) {
                // si ni siquiera existe el usuario, no hay pregunta que mostrar
            }

            model.addAttribute("username", username);
            model.addAttribute("error", ex.getMessage());

            return "mgwebsite/reset-password-answer";
        }
    }
}