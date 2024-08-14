package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.Match;
import com.escalabram.escalabram.service.dto.SearchForMatchDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    // TODO Passer la date en parametre
    @Query("SELECT DISTINCT new com.escalabram.escalabram.service.dto.SearchForMatchDTO( s.id, ts.id, ts.beginTime, ts.endTime) " +
            "FROM Search s " +
            "INNER JOIN s.timeSlots ts " +
            "WHERE s.placeId = :placeId " +
            "AND s.climberProfileId <> :climberProfileId " +
            "AND DATE(ts.beginTime) IN :dateList ")  // order by ?
    List<SearchForMatchDTO> findSearchesByMatchCriterias(
            @Param("climberProfileId") Long searchClimberProfile,
            @Param("placeId") Long placeId,
            @Param("dateList") List<Timestamp> dateList);
}