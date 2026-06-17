package com.microgames.mgwebsite.web.security;

import com.microgames.mgwebsite.web.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final LoginAttemptService loginAttemptService;

    public CustomAuthenticationProvider(CustomUserDetailsService userDetailsService,
                                        PasswordEncoder passwordEncoder,
                                        LoginAttemptService loginAttemptService) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();           // Lo que viene del formulario
        String password = (String) authentication.getCredentials();

        // 1. Verificar si la cuenta está bloqueada
        if (loginAttemptService.isBlocked(email)) {
            throw new BadCredentialsException("Cuenta bloqueada temporalmente por múltiples intentos fallidos.");
        }

        // 2. Cargar el usuario (aquí también se vuelve a chequear isBlocked)
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // 3. Verificar contraseña
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            loginAttemptService.loginFailed(email);   // ← Registro de intento fallido
            throw new BadCredentialsException("Email o contraseña incorrectos.");
        }

        // 4. Login exitoso
        loginAttemptService.loginSucceeded(email);

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,  // No guardamos la contraseña en el token
                userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}