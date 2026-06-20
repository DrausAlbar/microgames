package com.microgames.mgwebsite.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microgames.mgwebsite.web.entities.UserProfile;
import com.microgames.mgwebsite.web.entities.Usermain;

public interface UserProfileRepository
        extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUsermain(Usermain usermain);

}