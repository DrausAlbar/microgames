package com.microgames.mgwebsite.web.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;


@Component
public class AuthenticationHandlers 
    implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    private final LoginAttemptService loginAttemptService;

    public AuthenticationHandlers(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        String email = authentication.getName();
        loginAttemptService.loginSucceeded(email);
        response.sendRedirect("/home");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        
        String email = request.getParameter("email");

        if (email != null && !email.trim().isEmpty()) {
            loginAttemptService.loginFailed(email);
        }

        response.sendRedirect("/login?error=true");
    }
}