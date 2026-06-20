package com.microgames.mgwebsite.web.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class BruteForceFilter extends OncePerRequestFilter {

    private final LoginAttemptService loginAttemptService;

    public BruteForceFilter(
            LoginAttemptService loginAttemptService) {

        this.loginAttemptService = loginAttemptService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        if ("/login".equals(request.getServletPath())
                && "POST".equalsIgnoreCase(request.getMethod())) {

            String username =
                    request.getParameter("username");

            if (username != null
                    && loginAttemptService.isBlocked(username)) {

                response.sendRedirect(
                        "/login?locked");

                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}