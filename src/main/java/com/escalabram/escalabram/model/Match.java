package com.escalabram.escalabram.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long match_id;

    @Column(name = "matching_user_id", nullable = false)
    private Long matchingUserId;

    @Column(name = "matched_user_id", nullable = false)
    private Long matchedUserId;

    @Column(name = "mutual_match", nullable = false)
    private boolean mutualMatch;

    @Column(name = "match_date", nullable = false)
    private Date matchDate;

    public Match() {

    }

    public Match(Long match_id, Long matchingUserId, Long matchedUserId, boolean mutualMatch, Date matchDate) {
        this.match_id = match_id;
        this.matchingUserId = matchingUserId;
        this.matchedUserId = matchedUserId;
        this.mutualMatch = mutualMatch;
        this.matchDate = matchDate;
    }

    public Long getMatch_id() {
        return match_id;
    }

    public void setMatch_id(Long match_id) {
        this.match_id = match_id;
    }

    public Long getMatchingUserId() {
        return matchingUserId;
    }

    public void setMatchingUserId(Long matchingUserId) {
        this.matchingUserId = matchingUserId;
    }

    public Long getMatchedUserId() {
        return matchedUserId;
    }

    public void setMatchedUserId(Long matchedUserId) {
        this.matchedUserId = matchedUserId;
    }

    public boolean isMutualMatch() {
        return mutualMatch;
    }

    public void setMutualMatch(boolean mutualMatch) {
        this.mutualMatch = mutualMatch;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }
}
