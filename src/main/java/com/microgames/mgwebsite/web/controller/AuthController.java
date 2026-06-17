package com.microgames.mgwebsite.web.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.microgames.mgwebsite.web.entities.Userclient;
import com.microgames.mgwebsite.web.repository.UserRepository;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Mostrar formulario de registro
    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Userclient());
        return "mgwebsite/register";
    }

   
    // Procesar registro
    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute Userclient usuario, Model model) {

        if (userRepository.findByUsername(usuario.getUsername()).isPresent()) {
            model.addAttribute("error", "El nombre de usuario ya existe");
            return "register";
        }

        if (userRepository.findByEmail(usuario.getEmail()).isPresent()) { // Necesitarás agregar este método
            model.addAttribute("error", "El email ya está registrado");
            return "register";
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setEnabled(true);
        usuario.setEstaOnline(false);
        usuario.setRol("USER");

        userRepository.save(usuario);

        return "redirect:/mgwebsite/login?registered";
    }

    // Mostrar login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "mgwebsite/login.html";
    }

    // Página principal después de login
    @GetMapping("/home")
    public String home() {
        return "mgwebsite/home";
    }

    // ==================== API PARA VALIDACIÓN AJAX ====================
    
    @GetMapping("/api/auth/check-username")
    @ResponseBody
    public Map<String, Object> checkUsername(@RequestParam String username) {
        Map<String, Object> response = new HashMap<>();
        
   
        boolean disponible = userRepository.findByUsername(username).isEmpty();
        
        response.put("disponible", disponible);
        response.put("username", username);
        
        if (disponible) {
            response.put("mensaje", "✅ El nombre de usuario está disponible");
        } else {
            response.put("mensaje", "❌ Este nombre de usuario ya está en uso");
        }
        
        return response;
    }

  @GetMapping("/api/auth/check-email")
@ResponseBody
public Map<String, Object> checkEmail(@RequestParam String email) {
    Map<String, Object> response = new HashMap<>();
    
    if (userRepository.findByEmail(email).isPresent()) {
        response.put("disponible", false);
        response.put("mensaje", "Este email ya está registrado");
    } else {
        response.put("disponible", true);
        response.put("mensaje", "Email disponible ✓");
    }
    
    return response;
}

}
