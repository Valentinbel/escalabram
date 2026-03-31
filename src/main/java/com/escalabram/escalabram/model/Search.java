package com.escalabram.escalabram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name="search")
public class Search implements Serializable {

    @Serial
    private static final long serialVersionUID = 1689869441270067374L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 80, message = "Title cannot be longer than 80 characters")
    @Column(name = "title")
    private String title; //TODO comment ?

    @Column(name= "place_id")
    private Long placeId;

    @Column(name = "prefered_gender_id")
    private Long preferedGenderId;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "search", cascade = CascadeType.ALL)
    @Builder.Default
    @ToString.Exclude
    private Set<TimeSlot> timeSlots = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "search_climb_level",
            joinColumns = { @JoinColumn(name = "search_id") },
            inverseJoinColumns = { @JoinColumn(name = "climb_level_id") }
    )
    @Builder.Default
    @ToString.Exclude
    private Set<ClimbLevel> climbLevels = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @NotNull
    @JoinColumn(name = "profile_id", nullable = false)
    @ToString.Exclude
    private Profile profile;

    // TODO champs à ajouter (?)
    //min-max age
}
