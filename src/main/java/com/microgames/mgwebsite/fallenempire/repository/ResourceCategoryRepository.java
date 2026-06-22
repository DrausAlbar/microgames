package com.microgames.mgwebsite.fallenempire.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microgames.mgwebsite.fallenempire.entities.ResourceCategory;

public interface ResourceCategoryRepository
        extends JpaRepository<ResourceCategory, Long> {

    Optional<ResourceCategory> findByCode(String code);
}