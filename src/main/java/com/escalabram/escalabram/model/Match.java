package com.escalabram.escalabram.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="match")
public class Match implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matching_search_id", nullable = false)
    private Long matchingSearchId;

    @Column(name = "matched_search_id", nullable = false)
    private Long matchedSearchId;

    @Column(name = "mutual_match", nullable = false)
    private boolean mutualMatch;

    @Column(name = "match_date", nullable = false)
    private LocalDate matchDate;

    public Match() {

    }

    public Match(Long id, Long matchingSearchId, Long matchedSearchId, boolean mutualMatch, LocalDate matchDate) {
        this.id = id;
        this.matchingSearchId = matchingSearchId;
        this.matchedSearchId = matchedSearchId;
        this.mutualMatch = mutualMatch;
        this.matchDate = matchDate;
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

    public boolean isMutualMatch() {
        return mutualMatch;
    }

    public void setMutualMatch(boolean mutualMatch) {
        this.mutualMatch = mutualMatch;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }
}
