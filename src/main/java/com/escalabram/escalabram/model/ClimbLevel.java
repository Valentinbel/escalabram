package com.escalabram.escalabram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="climb_level")
public class ClimbLevel implements Serializable {

    @Id
    private Long id;

    @Column(name = "code_fr")
    private String codeFr;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "climbLevels")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClimbLevel that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getCodeFr(), that.getCodeFr()) && Objects.equals(getSearches(), that.getSearches());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCodeFr(), getSearches());
    }
}
