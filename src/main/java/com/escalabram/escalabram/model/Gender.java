package com.escalabram.escalabram.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="gender")
public class Gender implements Serializable {

    @Serial
    private static final long serialVersionUID = 2998787090387494728L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gender_name", nullable = false)
    private String genderName;
}
