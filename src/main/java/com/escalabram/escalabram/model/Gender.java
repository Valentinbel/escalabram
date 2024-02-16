package com.escalabram.escalabram.model;

import jakarta.persistence.*;

@Entity
@Table(name="gender")
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gender_id;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "code", nullable = false)
    private String code;

    public Gender() {

    }

    public Gender(Long gender_id, String label, String code) {
        this.gender_id = gender_id;
        this.label = label;
        this.code = code;
    }

    public Long getGender_id() {
        return gender_id;
    }

    public void setGender_id(Long gender_id) {
        this.gender_id = gender_id;
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
}
