package com.microgames.mgwebsite.web.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usermain")
public class Usermain {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(mappedBy = "usermain", cascade = CascadeType.ALL)
  private UserProfile profile;

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

  @Column(name = "signup_date", nullable = false, updatable = false)
  private LocalDateTime signupDate = LocalDateTime.now();

  @Column(name = "last_login_date")
  private LocalDateTime lastLoginDate;

  @Column(name = "is_online", nullable = false)
  private boolean estaOnline = false;

  // ==================== CONSTRUCTORES ====================
  public Usermain() {
  }

  // ==================== GETTERS Y SETTERS ====================

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRol() {
    return rol;
  }

  public void setRol(String rol) {
    this.rol = rol;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public LocalDateTime getSignupDate() {
    return signupDate;
  }

  public void setSignupDate(LocalDateTime signupDate) {
    this.signupDate = signupDate;
  }

  public LocalDateTime getLastLoginDate() {
    return lastLoginDate;
  }

  public void setLastLoginDate(LocalDateTime lastLoginDate) {
    this.lastLoginDate = lastLoginDate;
  }

  public boolean isEstaOnline() {
    return estaOnline;
  }

  public void setEstaOnline(boolean estaOnline) {
    this.estaOnline = estaOnline;
  }

  public UserProfile getProfile() {
    return profile;
  }

  public void setProfile(UserProfile profile) {
    this.profile = profile;

  }

}