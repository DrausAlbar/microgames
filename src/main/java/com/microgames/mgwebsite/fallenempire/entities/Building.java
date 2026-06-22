//Paquete del juego Fallen Empire.
package com.microgames.mgwebsite.fallenempire.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "fallenempire_building")
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre técnico único (ej: "MINAS", "REFINERIA_METALURGICA")
    @Column(nullable = false, unique = true, length = 50)
    private String code;

    // Nombre visible al jugador (ej: "Refinería Metalúrgica")
    @Column(nullable = false, length = 100)
    private String name;

    public Building() {
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
}
