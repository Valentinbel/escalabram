package com.escalabram.escalabram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="climb_level")
public class ClimbLevel implements Serializable {

    @Serial
    private static final long serialVersionUID = -2093459699877697906L;

    @Id
    private Long id;

    @Column(name = "code_fr")
    private String codeFr;

    @ManyToMany( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "climbLevels")
    @JsonIgnore
    @Builder.Default
    private Set<Search> searches = new HashSet<>();
}
