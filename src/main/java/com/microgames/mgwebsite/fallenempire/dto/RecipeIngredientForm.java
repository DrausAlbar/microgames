package com.microgames.mgwebsite.fallenempire.dto;

public class RecipeIngredientForm {

    private Long recipeId;
    private Long resourceId;
    private int quantity = 1;

    public RecipeIngredientForm() {
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}