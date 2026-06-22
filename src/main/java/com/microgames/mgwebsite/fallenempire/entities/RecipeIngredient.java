//Paquete del juego Fallen Empire.
package com.microgames.mgwebsite.fallenempire.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "fallenempire_recipe_ingredient")
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // La receta a la que pertenece este ingrediente
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    // El recurso que se usa como ingrediente (ej: "Hematita")
    @ManyToOne
    @JoinColumn(name = "resource_id", nullable = false)
    private ResourceType resource;

    // Cuántas unidades de este recurso se necesitan
    @Column(nullable = false)
    private int quantity = 1;

    public RecipeIngredient() {
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public ResourceType getResource() {
        return resource;
    }

    public void setResource(ResourceType resource) {
        this.resource = resource;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
