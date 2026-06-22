package com.microgames.mgwebsite.fallenempire.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "fallenempire_resource_type")
public class ResourceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre técnico único, usado en código (ej: "HEMATITA", "ENERGIA")
    @Column(nullable = false, unique = true, length = 50)
    private String code;

    // Nombre visible al jugador (ej: "Hematita")
    @Column(nullable = false, length = 50)
    private String name;

    // La etapa de producción de este recurso (ej: "Refinados")
    @ManyToOne
    @JoinColumn(name = "tier_id", nullable = false)
    private ResourceTier tier;

    // La categoría de este recurso (ej: "Químicos").
    // No tiene jerarquía: es una simple etiqueta de tipo de material.
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ResourceCategory category;

    // Qué tan común es este recurso en el mapa (Alta, Media, Baja).
    // Solo aplica a recursos extraídos directamente (tiers iniciales); el resto puede dejarse null.
    @Column(length = 20)
    private String abundance;

    // Descripción opcional para mostrar al jugador
    @Column(length = 255)
    private String description;

    public ResourceType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceTier getTier() {
        return tier;
    }

    public void setTier(ResourceTier tier) {
        this.tier = tier;
    }

    public ResourceCategory getCategory() {
        return category;
    }

    public void setCategory(ResourceCategory category) {
        this.category = category;
    }

    public String getAbundance() {
        return abundance;
    }

    public void setAbundance(String abundance) {
        this.abundance = abundance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}