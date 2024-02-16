package com.escalabram.escalabram.model;

import jakarta.persistence.*;

@Entity
@Table(name="user_escalabram")
public class UserEscalabram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    // FIXME champs à ajouter (?) Spring Security
    // accessToken string
    //rememberMeToken string

    // FIXME Relations tables
    // hasOne(() => Profile)
    //public profile: HasOne<typeof Profile>

    public UserEscalabram() {

    }

    public UserEscalabram(Long user_id, String name, String email, String password) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
}
