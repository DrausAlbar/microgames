package com.microgames.mgwebsite.fallenempire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microgames.mgwebsite.fallenempire.entities.Recipe;
import com.microgames.mgwebsite.fallenempire.entities.RecipeIngredient;
import com.microgames.mgwebsite.fallenempire.entities.ResourceType;

public interface RecipeIngredientRepository
        extends JpaRepository<RecipeIngredient, Long> {

    // Todos los ingredientes de una receta
    List<RecipeIngredient> findByRecipe(Recipe recipe);

    // En qué recetas se usa un recurso como ingrediente
    // (útil a futuro para saber "¿en qué se usa la Hematita?")
    List<RecipeIngredient> findByResource(ResourceType resource);
}
