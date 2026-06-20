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

    // 0 = flujo instantáneo (Energía, Agua, Puntos...)
    // 1 = Menas, Minerales, Gases base, Orgánicos
    // 2 = Refinados (Metales, Químicos, Alimentos...)
    // 3 = Industriales (Materiales, Componentes...)
    // 4 = Comerciales (Piezas de naves, productos de lujo...)
    @Column(nullable = false)
    private int tier;

    // La categoría específica de este recurso (ej: "Químicos").
    // Si necesitas la categoría padre (ej: "Orgánicos"), se obtiene
    // con resourceType.getCategory().getParent()
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ResourceCategory category;

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

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public ResourceCategory getCategory() {
        return category;
    }

    public void setCategory(ResourceCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}