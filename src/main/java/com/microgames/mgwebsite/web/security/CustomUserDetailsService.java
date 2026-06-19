package com.microgames.mgwebsite.web.security;

import com.microgames.mgwebsite.web.entities.Usermain;
import com.microgames.mgwebsite.web.repository.UsermainRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsermainRepository repository;
        
    public CustomUserDetailsService(UsermainRepository repository) {
        this.repository = repository;
    }

  @Override
public UserDetails loadUserByUsername(String login)
        throws UsernameNotFoundException {

    Usermain user = repository
            .findByUsernameIgnoreCase(login)
            .or(() -> repository.findByEmailIgnoreCase(login))
            .orElseThrow(() ->
                    new UsernameNotFoundException(
                            "Usuario no encontrado - Mensaje de error s-cuds-28: "
                                    + login));
        return new User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" + user.getRol()))
        );
    }
}