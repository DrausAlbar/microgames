package com.microgames.mgwebsite.fallenempire.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microgames.mgwebsite.fallenempire.entities.ResourceCategory;

public interface ResourceCategoryRepository
        extends JpaRepository<ResourceCategory, Long> {

    Optional<ResourceCategory> findByCode(String code);

    // Categorías raíz (sin padre) — útil para mostrar el árbol desde arriba
    List<ResourceCategory> findByParentIsNull();

    // Subcategorías directas de una categoría dada
    List<ResourceCategory> findByParent(ResourceCategory parent);
}