package com.microgames.mgwebsite.web.entities;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "usermain_profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usermain_id", unique = true)
    private Usermain usermain;

    @Column(length = 16)
    private String firstName;

    @Column(length = 16)
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(nullable = false)
    private int avatarId = 1;

    // Constructor vacío
    public UserProfile() {
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usermain getUsermain() {
        return usermain;
    }

    public void setUsermain(Usermain usermain) {
        this.usermain = usermain;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

}
