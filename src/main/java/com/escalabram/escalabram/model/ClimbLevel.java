package com.escalabram.escalabram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="climb_level")
public class ClimbLevel implements Serializable {

    @Serial
    private static final long serialVersionUID = -2093459699877697906L;

    @Id
    private Long id;

    @Column(name = "code_fr")
    private String codeFr;

    @ManyToMany( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "climbLevels")
    @JsonIgnore
    private Set<Search> searches = new HashSet<>();

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

    public Set<Search> getSearches() {
        return searches;
    }

    public void setSearches(Set<Search>searches) {
        this.searches = searches;
    }

    @Override
    public String toString() {
        return "ClimbLevel{" +
                "id=" + id +
                ", codeFr='" + codeFr +
                '}';
    }
}
