package com.escalabram.escalabram.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Size(max = 8, message = "GenderName can't have more than 8 char")
    @Column(name = "gender_name", length = 8, nullable = false, unique = true)
    private String genderName;
}
