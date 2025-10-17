package com.escalabram.escalabram.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="climber_profile")
public class ClimberProfile implements Serializable {

    @Serial
    private static final long serialVersionUID = -2789592186380559249L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gender_id")
    private Long genderId;

    @Column(name = "language_id")
    private Long languageId;

    @Column(name = "is_notified", nullable = false)
    private Boolean isNotified;

    @Column(columnDefinition = "TEXT", name = "climber_profile_description")
    private String climberProfileDescription;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "climber_user_id", referencedColumnName = "id", nullable = false)
    private ClimberUser climberUser;

    // TODO Gerer les relations de table
    //hasOne Gender
    //hasOne Language
    //hasManySearch ==> Comprendre les différents types de Cascade et autres parametres
}
