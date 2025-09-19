package com.escalabram.escalabram.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
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

    public Match() {

    }

    public Match(Long id, Long matchingSearchId, Long matchedSearchId, Long matchedTimeSlotId, Boolean mutualMatch) {
        this.id = id;
        this.matchingSearchId = matchingSearchId;
        this.matchedSearchId = matchedSearchId;
        this.matchedTimeSlotId = matchedTimeSlotId;
        this.mutualMatch = mutualMatch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatchingSearchId() {
        return matchingSearchId;
    }

    public void setMatchingSearchId(Long matchingSearchId) {
        this.matchingSearchId = matchingSearchId;
    }

    public Long getMatchedSearchId() {
        return matchedSearchId;
    }

    public void setMatchedSearchId(Long matchedSearchId) {
        this.matchedSearchId = matchedSearchId;
    }

    public Long getMatchedTimeSlotId() {
        return matchedTimeSlotId;
    }

    public void setMatchedTimeSlotId(Long matchedTimeSlotId) {
        this.matchedTimeSlotId = matchedTimeSlotId;
    }

    public Boolean isMutualMatch() {
        return mutualMatch;
    }

    public void setMutualMatch(Boolean mutualMatch) {
        this.mutualMatch = mutualMatch;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", matchingSearchId=" + matchingSearchId +
                ", matchedSearchId=" + matchedSearchId +
                ", matchedTimeSlotId=" + matchedTimeSlotId +
                ", mutualMatch=" + mutualMatch +
                '}';
    }
}
