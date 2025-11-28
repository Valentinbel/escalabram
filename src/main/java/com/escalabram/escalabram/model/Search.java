package com.escalabram.escalabram.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="search")
public class Search implements Serializable {

    @Serial
    private static final long serialVersionUID = 1689869441270067374L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "climber_profile_id", nullable = false)
    private Long climberProfileId;

    @Size(max = 80, message = "Title cannot be longer than 80 characters")
    @Column(name = "title")
    private String title;

    @Column(name = "have_rope")
    private Boolean haveRope;

    @Column(name = "have_belay_device")
    private Boolean haveBelayDevice;

    @Column(name = "have_quickdraw")
    private Boolean haveQuickdraw;

    @Column(name = "have_car_to_share")
    private Boolean haveCarToShare;

    @Column(name= "place_id")
    private Long placeId;

    @Column(name = "prefered_gender_id")
    private Long preferedGenderId;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "search", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<TimeSlot> timeSlots = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "search_climb_level",
            joinColumns = { @JoinColumn(name = "search_id") },
            inverseJoinColumns = { @JoinColumn(name = "climb_level_id") }
    )
    @Builder.Default
    private Set<ClimbLevel> climbLevels = new HashSet<>();

    // TODO champs à ajouter (?)
    //min-max age
}
