package com.escalabram.escalabram.model;

import jakarta.persistence.*;

@Entity
@Table(name="climb_level")
public class ClimbLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code_fr", nullable = false)
    private String codeFr;

    public ClimbLevel() {
    }

    public ClimbLevel(Long id, String codeFr) {
        this.id = id;
        this.codeFr = codeFr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeFr() {
        return codeFr;
    }

    public void setCodeFr(String codeFr) {
        this.codeFr = codeFr;
    }
}
