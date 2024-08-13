package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.Match;
import com.escalabram.escalabram.service.dto.SearchForMatchDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    // TODO Passer la date en parametre
    @Query("SELECT DISTINCT new com.escalabram.escalabram.service.dto.SearchForMatchDTO( s.id, ts.beginTime, ts.endTime) " +
            "FROM Search s " +
            "INNER JOIN s.timeSlots ts " +
            "WHERE s.placeId = :placeId " +
            "AND s.climberProfileId <> :climberProfileId " +
            "AND DATE(ts.beginTime) = DATE('2024-09-02') ")
    List<SearchForMatchDTO> findSearchesByMatchCriterias(
            @Param("climberProfileId") Long searchClimberProfile,
            @Param("placeId") Long placeId);
}