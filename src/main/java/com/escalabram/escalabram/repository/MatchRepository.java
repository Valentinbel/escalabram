package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query(value = "SELECT * " +
            "FROM Match m " +
            "WHERE m.matching_search_id = :matchingSearchId " +
            "AND m.matched_search_id = :matchedSearchId " +
            "AND m.matched_time_slot_id = :matchedTimeSlotId " +
            "AND m.mutual_match = :mutualMatch " +
            "LIMIT 1 ", nativeQuery = true)
    Optional<Match> findByCriterias(@Param("matchingSearchId") Long matchingSearchId,
                                    @Param("matchedSearchId") Long matchedSearchId,
                                    @Param("matchedTimeSlotId") Long matchedTimeSlotId,
                                    @Param("mutualMatch") boolean mutualMatch);
}