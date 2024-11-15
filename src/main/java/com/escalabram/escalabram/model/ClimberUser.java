package com.escalabram.escalabram.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="climber_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "userName"),
                @UniqueConstraint(columnNames = "email")
        })
public class ClimberUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 5685392521253805062L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 4, max = 20, message = "userName should be between 4 to 20 characters")
    @Column(name = "user_name", unique = true)
    private String userName;

    @NotBlank
    @Size(max = 50, message = "Email cannot be longer than 50 characters")
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(max = 120) //c'est le password crypté
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "climber_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_user_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // TODO champs à ajouter (?) Spring Security
    // accessToken string
    //rememberMeToken string

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "climber_profile_id",
            referencedColumnName = "id")
    private ClimberProfile climberProfile;

    public ClimberUser() {

    }

    public ClimberUser(String userName,
                       String email,
                       String password,
                       LocalDateTime createdAt,
                       LocalDateTime updatedAt
    ) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ClimberProfile getClimberProfile() {
        return climberProfile;
    }

    public void setClimberProfile(ClimberProfile climberProfile) {
        this.climberProfile = climberProfile;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "ClimberUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", climberProfile=" + climberProfile +
                '}';
    }
}
