package com.microgames.mgwebsite.web.security;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.microgames.mgwebsite.web.repository.UsermainRepository;

//Es para el antibruteforce
@Component
public class AuthenticationHandlers
        implements AuthenticationSuccessHandler,
        AuthenticationFailureHandler {

    private final LoginAttemptService loginAttemptService;
    private final UsermainRepository userRepository;

    public AuthenticationHandlers(
            LoginAttemptService loginAttemptService,
            UsermainRepository userRepository) {

        this.loginAttemptService = loginAttemptService;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        String username = authentication.getName();

        loginAttemptService.loginSucceeded(username);

        userRepository
                .findByUsernameIgnoreCase(username)
                .ifPresent(user -> {

                    user.setLastLoginDate(
                            LocalDateTime.now());

                    userRepository.save(user);
                });

        response.sendRedirect("/home");
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException {

        String login = request.getParameter("username");

        userRepository
                .findByUsernameIgnoreCase(login)
                .or(() -> userRepository.findByEmailIgnoreCase(login))
                .ifPresent(user -> loginAttemptService.loginFailed(
                        user.getUsername()));

        response.sendRedirect("/login?error");
    }

}