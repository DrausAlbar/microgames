package com.microgames.mgwebsite.fallenempire.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microgames.mgwebsite.fallenempire.entities.Building;

public interface BuildingRepository
        extends JpaRepository<Building, Long> {

    Optional<Building> findByCode(String code);
}
