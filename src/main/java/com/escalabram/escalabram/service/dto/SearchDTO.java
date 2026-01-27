package com.escalabram.escalabram.service.dto;

import com.escalabram.escalabram.model.ClimbLevel;
import com.escalabram.escalabram.model.TimeSlot;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO implements Serializable {

    private Long id;

    private Set<TimeSlot> timeSlots;

    private Set<ClimbLevel> climbLevels;

    private Long placeId;

    private Long preferedGenderId;

    @NotNull // TODO verifier que ca marche
    Long profileId;
}
