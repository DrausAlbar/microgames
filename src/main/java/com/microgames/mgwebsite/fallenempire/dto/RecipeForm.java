package com.microgames.mgwebsite.fallenempire.dto;

public class RecipeForm {

    private Long buildingId;
    private Long outputResourceId;

    public RecipeForm() {
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getOutputResourceId() {
        return outputResourceId;
    }

    public void setOutputResourceId(Long outputResourceId) {
        this.outputResourceId = outputResourceId;
    }
}