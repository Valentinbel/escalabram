package com.escalabram.escalabram.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="gender")
public class Gender implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gender_name", nullable = false)
    private String genderName;

    public Gender() {
    }

    public Gender(Long id, String genderName) {
        this.id = id;
        this.genderName = genderName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    @Override
    public String toString() {
        return "Gender{" +
                "id=" + id +
                ", genderName='" + genderName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gender gender)) return false;
        return Objects.equals(getId(), gender.getId()) && Objects.equals(getGenderName(), gender.getGenderName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getGenderName());
    }
}
