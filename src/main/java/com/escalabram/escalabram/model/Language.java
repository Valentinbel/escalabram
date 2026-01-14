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
@Table(name="language")
public class Language implements Serializable {

    @Serial
    private static final long serialVersionUID = -4866619795587497870L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 3, message = "Code can't have more than 3 char")
    @Column(name = "code", length = 3, nullable = false, unique = true)
    private String code;

    @NotBlank
    @Size(max = 10, message = "Label can't have more than 10 char")
    @Column(name = "label", length = 10, nullable = false, unique = true)
    private String label;
}
