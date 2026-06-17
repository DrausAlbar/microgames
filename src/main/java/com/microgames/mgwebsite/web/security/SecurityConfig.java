package com.microgames.mgwebsite.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final AuthenticationHandlers authenticationHandlers;
    private final LoginAttemptService loginAttemptService;

    // Constructor con inyección
    public SecurityConfig(UserDetailsService userDetailsService, 
                          AuthenticationHandlers authenticationHandlers,LoginAttemptService loginAttemptService) {
        this.userDetailsService = userDetailsService;
        this.authenticationHandlers = authenticationHandlers;
        this.loginAttemptService = loginAttemptService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

@Bean
public AuthenticationProvider authenticationProvider() {
    return new CustomAuthenticationProvider(
        (CustomUserDetailsService) userDetailsService, 
        passwordEncoder(), 
        loginAttemptService
    );
}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**").permitAll()
                .requestMatchers("/", "/login", "/register", "/registrar").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(authenticationHandlers)
                .failureHandler(authenticationHandlers)
                .defaultSuccessUrl("/home", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .authenticationProvider(authenticationProvider());

        return http.build();
    }
}