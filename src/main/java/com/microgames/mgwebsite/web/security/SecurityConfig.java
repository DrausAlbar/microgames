package com.microgames.mgwebsite.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final BruteForceFilter bruteForceFilter;
    private final AuthenticationHandlers authenticationHandlers;

    public SecurityConfig(
            BruteForceFilter bruteForceFilter,
            AuthenticationHandlers authenticationHandlers) {

        this.bruteForceFilter = bruteForceFilter;
        this.authenticationHandlers = authenticationHandlers;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

            .addFilterBefore(
                    bruteForceFilter,
                    UsernamePasswordAuthenticationFilter.class)

            .authorizeHttpRequests(auth -> auth

                    .requestMatchers(
                            "/",
                            "/login",
                            "/register",
                            "/api/register/**",
                            "/css/**",
                            "/js/**",
                            "/images/**")
                    .permitAll()

                    .anyRequest()
                    .authenticated())

            .formLogin(form -> form

                    .loginPage("/login")

                    .successHandler(authenticationHandlers)

                    .failureHandler(authenticationHandlers)

                    .permitAll())

            .logout(logout -> logout

                    .logoutSuccessUrl("/login?logout"));

        return http.build();
    }
}