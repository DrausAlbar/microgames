package com.microgames.mgwebsite.web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.microgames.mgwebsite.web.entities.Userclient;
import com.microgames.mgwebsite.web.repository.UserRepository;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; 
    private final LoginAttemptService loginAttemptService;

    public CustomUserDetailsService(UserRepository userRepository, 
                                    LoginAttemptService loginAttemptService) {
        this.userRepository = userRepository;
        this.loginAttemptService = loginAttemptService;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

if (loginAttemptService.isBlocked(email)) {
            throw new UsernameNotFoundException("Cuenta bloqueada temporalmente por múltiples intentos fallidos. Intente más tarde.");
        }


        Userclient usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol());

        return new User(
                  usuario.getEmail(),            // ← Cambiado a email
                usuario.getPassword(),
                usuario.isEnabled(),
                true, true, true,
                Collections.singletonList(authority)
        );
    }
}