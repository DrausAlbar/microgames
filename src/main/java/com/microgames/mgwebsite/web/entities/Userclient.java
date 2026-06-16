package com.microgames.mgwebsite.web.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Userclient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String rol = "USER";

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "ultima_fecha_login")
    private LocalDateTime ultimaFechaLogin;

    @Column(name = "esta_online")
    private boolean estaOnline = false;

    @Column(length = 50)
    private String firstname;
    
    @Column(length = 50)
    private String lastname;
    // ==================== CONSTRUCTORES ====================
    public Userclient() {
    }

    // ==================== GETTERS Y SETTERS ====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public LocalDateTime getUltimaFechaLogin() { return ultimaFechaLogin; }
    public void setUltimaFechaLogin(LocalDateTime ultimaFechaLogin) { this.ultimaFechaLogin = ultimaFechaLogin; }

    public boolean isEstaOnline() { return estaOnline; }
    public void setEstaOnline(boolean estaOnline) { this.estaOnline = estaOnline; }

       public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

       public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.username = lastname; }
    
}