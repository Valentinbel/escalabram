package com.escalabram.escalabram.model;

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
@Table(name="profile")
public class Profile implements Serializable {

    @Serial
    private static final long serialVersionUID = -2789592186380559249L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gender_id")
    private Long genderId;

    @Column(name = "is_notified", nullable = false)
    private Boolean isNotified;

    @Column(columnDefinition = "TEXT", name = "profile_description")
    private String profileDescription;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Search> searches = new HashSet<>();

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

//    @Column(name = "have_rope")
//    private Boolean haveRope;
//
//    @Column(name = "have_belay_device")
//    private Boolean haveBelayDevice;
//
//    @Column(name = "have_quickdraw")
//    private Boolean haveQuickdraw;
//
//    @Column(name = "have_car_to_share")
//    private Boolean haveCarToShare;

    // TODO Gerer les relations de table
    //hasOne Gender
}
