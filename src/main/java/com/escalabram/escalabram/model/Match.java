package com.escalabram.escalabram.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="match")
public class Match implements Serializable {

    @Serial
    private static final long serialVersionUID = -6078432217702053187L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matching_search_id", nullable = false)
    private Long matchingSearchId;

    @Column(name = "matched_search_id", nullable = false)
    private Long matchedSearchId;

    @Column(name = "matched_time_slot_id", nullable = false)
    private Long matchedTimeSlotId;

    @Column(name = "mutual_match", nullable = false)
    private Boolean mutualMatch;
}
