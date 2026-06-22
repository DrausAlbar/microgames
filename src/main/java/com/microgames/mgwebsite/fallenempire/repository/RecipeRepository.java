package com.microgames.mgwebsite.fallenempire.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microgames.mgwebsite.fallenempire.entities.Recipe;
import com.microgames.mgwebsite.fallenempire.entities.ResourceType;

public interface RecipeRepository
        extends JpaRepository<Recipe, Long> {

    // La receta que produce un recurso en concreto (ej: la receta del Acero)
    Optional<Recipe> findByOutputResource(ResourceType outputResource);
}
