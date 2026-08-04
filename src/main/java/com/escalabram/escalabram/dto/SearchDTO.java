package com.escalabram.escalabram.dto;

import com.escalabram.escalabram.model.ClimbLevel;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO implements Serializable {

    private Long id;

    @NotNull
    private Set<LocalDateTime> timeSlots;

    @NotNull
    private Set<ClimbLevel> climbLevels;

    @NotNull
    private Long placeId;

    private String title; //TODO rename to comment  o something else?

    private Long preferedGenderId;

    @NotNull
    Long profileId;
}
