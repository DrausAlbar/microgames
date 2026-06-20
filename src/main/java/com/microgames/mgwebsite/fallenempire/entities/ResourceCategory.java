package com.microgames.mgwebsite.fallenempire.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "fallenempire_resource_category")
public class ResourceCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre técnico único (ej: "ORGANICOS", "QUIMICOS")
    @Column(nullable = false, unique = true, length = 50)
    private String code;

    // Nombre visible al jugador (ej: "Orgánicos")
    @Column(nullable = false, length = 50)
    private String name;

    // La categoría padre. Si es null, es una categoría raíz (tope del árbol).
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ResourceCategory parent;

    // Las subcategorías hijas de esta categoría.
    @OneToMany(mappedBy = "parent")
    private List<ResourceCategory> children = new ArrayList<>();

    public ResourceCategory() {
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

    public ResourceCategory getParent() {
        return parent;
    }

    public void setParent(ResourceCategory parent) {
        this.parent = parent;
    }

    public List<ResourceCategory> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceCategory> children) {
        this.children = children;
    }
}