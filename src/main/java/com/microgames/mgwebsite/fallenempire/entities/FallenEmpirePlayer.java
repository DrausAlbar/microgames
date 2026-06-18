//Paquete del juego Fallen Empire.
package com.microgames.mgwebsite.fallenempire.entities;

import com.microgames.mgwebsite.web.entities.Usermain;
import jakarta.persistence.*;

@Entity
@Table(name = "fallenempire_player")
public class FallenEmpirePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "usermain_id", unique = true)
    private Usermain usermain;


    // Constructor vacío
    public FallenEmpirePlayer() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
   public Usermain getUsermain() {
    return usermain;
}

}
