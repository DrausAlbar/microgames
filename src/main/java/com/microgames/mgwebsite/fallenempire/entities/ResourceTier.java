//Paquete del juego Fallen Empire.
package com.microgames.mgwebsite.fallenempire.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "fallenempire_resource_tier")
public class ResourceTier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre técnico único (ej: "BASE", "RECURSOS", "REFINADOS")
    @Column(nullable = false, unique = true, length = 50)
    private String code;

    // Nombre visible al jugador (ej: "Refinados")
    @Column(nullable = false, length = 50)
    private String name;

    // Orden de la etapa dentro de la cadena de producción.
    // Ej: Base=0, Recursos=1, Refinados=2, Industriales=3, Fabricas=4.
    // Sirve para ordenar el árbol de recursos en pantalla, de lo más
    // básico a lo más elaborado.
    @Column(nullable = false)
    private int orderIndex;

    public ResourceTier() {
    }

    // Getters y Setters

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

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }
}