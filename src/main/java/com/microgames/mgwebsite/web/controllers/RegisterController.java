package com.microgames.mgwebsite.web.controllers;

import com.microgames.mgwebsite.web.dto.RegisterRequest;
import com.microgames.mgwebsite.web.service.RegisterService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute(
                "registerRequest",
                new RegisterRequest());

        return "mgwebsite/register";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute RegisterRequest registerRequest,
            Model model) {

        try {

            registerService.register(registerRequest);

            return "redirect:/login?registered";

        } catch (IllegalArgumentException ex) {

            model.addAttribute(
                    "registerRequest",
                    registerRequest);

            model.addAttribute(
                    "error",
                    ex.getMessage());

            return "/mgwebsite/register";
        }
    }
}