package com.escalabram.escalabram.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="language")
public class Language implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "code", nullable = false)
    private String code;

    public Language() {

    }

    public Language(Long id, String label, String code) {
        this.id = id;
        this.label = label;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Language language)) return false;
        return Objects.equals(getId(), language.getId()) && Objects.equals(getLabel(), language.getLabel()) && Objects.equals(getCode(), language.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLabel(), getCode());
    }
}
