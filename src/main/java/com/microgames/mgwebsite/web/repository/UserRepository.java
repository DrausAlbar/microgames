package com.microgames.mgwebsite.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microgames.mgwebsite.web.entities.Userclient;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Userclient, Long> {
    Optional<Userclient> findByUsername(String username);
    Optional<Userclient> findByEmail(String email);    
    Optional<Userclient> findById(String id);

}
