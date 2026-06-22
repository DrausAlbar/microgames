package com.microgames.mgwebsite.fallenempire.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microgames.mgwebsite.fallenempire.entities.ResourceTier;

public interface ResourceTierRepository
        extends JpaRepository<ResourceTier, Long> {

    Optional<ResourceTier> findByCode(String code);

    // Para mostrar el árbol completo ordenado de Base a Fabricas
    List<ResourceTier> findAllByOrderByOrderIndexAsc();
}