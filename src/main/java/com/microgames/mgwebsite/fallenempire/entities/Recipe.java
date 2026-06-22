//Paquete del juego Fallen Empire.
package com.microgames.mgwebsite.fallenempire.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "fallenempire_recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // El edificio que produce esta receta (ej: "Refinería Metalúrgica")
    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    // El recurso que se obtiene al completar esta receta (ej: "Acero")
    // unique = true porque, por ahora, cada recurso tiene una única receta para producirlo.
    @OneToOne
    @JoinColumn(name = "output_resource_id", nullable = false, unique = true)
    private ResourceType outputResource;

    // Los ingredientes necesarios para esta receta (ej: Hematita, Grafito)
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    public Recipe() {
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public ResourceType getOutputResource() {
        return outputResource;
    }

    public void setOutputResource(ResourceType outputResource) {
        this.outputResource = outputResource;
    }

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
