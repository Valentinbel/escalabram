package com.escalabram.escalabram.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "refreshtoken")
public class RefreshToken implements Serializable {

    @Serial
    private static final long serialVersionUID = -5383103837484236543L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "climber_user_id", referencedColumnName = "id")
    private ClimberUser climberUser;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;
}
