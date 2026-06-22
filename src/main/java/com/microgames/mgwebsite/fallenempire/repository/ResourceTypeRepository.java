package com.microgames.mgwebsite.fallenempire.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microgames.mgwebsite.fallenempire.entities.ResourceType;

public interface ResourceTypeRepository
        extends JpaRepository<ResourceType, Long> {

    Optional<ResourceType> findByCode(String code);

    // Para listar todos los recursos ordenados por nombre en los <select> del admin
    List<ResourceType> findAllByOrderByNameAsc();
}