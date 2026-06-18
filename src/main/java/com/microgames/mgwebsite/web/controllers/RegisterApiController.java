package com.microgames.mgwebsite.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.microgames.mgwebsite.web.repository.UsermainRepository;

@Controller
@RequestMapping("/api/register")
public class RegisterApiController {

    private final UsermainRepository userRepository;

    public RegisterApiController(
            UsermainRepository userRepository) {

        this.userRepository = userRepository;
    }

    @ResponseBody
    @GetMapping("/username")
    public boolean usernameAvailable(
            @RequestParam String username) {

        return userRepository
                .findByUsernameIgnoreCase(username)
                .isEmpty();
    }

    @ResponseBody
    @GetMapping("/email")
    public boolean emailAvailable(
            @RequestParam String email) {

        return userRepository
                .findByEmail(email)
                .isEmpty();
    }
}