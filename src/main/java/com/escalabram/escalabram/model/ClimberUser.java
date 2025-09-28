package com.escalabram.escalabram.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="climber_user")
public class ClimberUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 5685392521253805062L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 4, max = 20, message = "userName should be between 4 to 20 characters")
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @NotBlank
    @Size(max = 50, message = "Email cannot be longer than 50 characters")
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(max = 120) //c'est le password crypté
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "climber_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_user_id"))
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @NotBlank
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
