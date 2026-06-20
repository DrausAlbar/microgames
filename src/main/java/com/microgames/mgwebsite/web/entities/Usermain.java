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

    // ==================== RECUPERACIÓN DE CONTRASEÑA ====================
 
  @Column(name = "secret_question", length = 255)
  private String secretQuestion;
 
  // Se guarda hasheada (BCrypt), igual que la contraseña. Nunca en texto plano.
  @Column(name = "secret_answer_hash")
  private String secretAnswerHash;
 
  // Timestamp del último intento de recuperación (correcto o incorrecto).
  // Mientras esté dentro de la ventana de bloqueo, no se permiten nuevos intentos.
  @Column(name = "last_password_attempt_date")
  private LocalDateTime lastPasswordAttemptDate;
 
// Cuenta los intentos fallidos seguidos al responder la pregunta secreta.
// Se resetea a 0 cuando la respuesta es correcta.
@Column(name = "failed_password_attempts", nullable = false)
private int failedPasswordAttempts = 0;

// Si la cuenta está bloqueada para recuperación, hasta cuándo.
@Column(name = "password_locked_until")
private LocalDateTime passwordLockedUntil;


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
 public String getSecretQuestion() {
    return secretQuestion;
  }

  public void setSecretQuestion(String secretQuestion) {
    this.secretQuestion = secretQuestion;
  }

  public String getSecretAnswerHash() {
    return secretAnswerHash;
  }

  public void setSecretAnswerHash(String secretAnswerHash) {
    this.secretAnswerHash = secretAnswerHash;
  }

  public LocalDateTime getLastPasswordAttemptDate() {
    return lastPasswordAttemptDate;
  }

  public void setLastPasswordAttemptDate(LocalDateTime lastPasswordAttemptDate) {
    this.lastPasswordAttemptDate = lastPasswordAttemptDate;
  }

  public int getFailedPasswordAttempts() {
  return failedPasswordAttempts;
}

public void setFailedPasswordAttempts(int failedPasswordAttempts) {
  this.failedPasswordAttempts = failedPasswordAttempts;
}

public LocalDateTime getPasswordLockedUntil() {
  return passwordLockedUntil;
}

public void setPasswordLockedUntil(LocalDateTime passwordLockedUntil) {
  this.passwordLockedUntil = passwordLockedUntil;
}
}