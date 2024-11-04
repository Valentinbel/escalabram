package com.escalabram.escalabram.model;

import com.escalabram.escalabram.model.enumeration.EnumRole;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "role")
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = -4742580104110659578L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EnumRole roleName;

    public Role() {
    }

    public Role(EnumRole roleName) {
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumRole getName() {
        return roleName;
    }

    public void setName(EnumRole roleName) {
        this.roleName = roleName;
    }
}
