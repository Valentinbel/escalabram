package com.escalabram.escalabram.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="climber_user")
public class ClimberUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    // TODO champs à ajouter (?) Spring Security
    // accessToken string
    //rememberMeToken string

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "climber_profile_id", referencedColumnName = "id")
    private ClimberProfile climberProfile;

    public ClimberUser() {

    }

    public ClimberUser(Long id, String name, String email, String password, ClimberProfile climberProfile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.climberProfile = climberProfile;
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ClimberProfile getClimberProfile() {
        return climberProfile;
    }

    public void setClimberProfile(ClimberProfile climberProfile) {
        this.climberProfile = climberProfile;
    }

    @Override
    public String toString() {
        return "ClimberUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", climberProfile='" + climberProfile + '\'' +
                '}';
    }
}
