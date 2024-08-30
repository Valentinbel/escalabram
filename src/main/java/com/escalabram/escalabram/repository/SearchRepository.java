package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.service.dto.ISearchClimbLevelDTO;
import com.escalabram.escalabram.service.dto.SearchMatchDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.*;

public interface SearchRepository extends JpaRepository<Search, Long> {

    Optional<Set<Search>> findByClimberProfileId(Long climberProfileId);

    @Query("SELECT DISTINCT new com.escalabram.escalabram.service.dto.SearchMatchDTO( s.id, ts.id, ts.beginTime, ts.endTime) " +
            "FROM Search s " +
            "INNER JOIN s.timeSlots ts " +
            "WHERE s.placeId = :placeId " +
            "AND s.climberProfileId <> :climberProfileId " +
            "AND DATE(ts.beginTime) IN :matchingBeginTimes ")
    List<SearchMatchDTO> findAllSearchesByCriterias(
            @Param("climberProfileId") Long searchClimberProfile,
            @Param("placeId") Long placeId,
            @Param("matchingBeginTimes") List<Timestamp> matchingBeginTimes);

    @Query(value ="SELECT DISTINCT s.id as searchid, scl.climb_level_id as climblevelid " +
            "FROM Search s " +
            "INNER JOIN search_climb_level scl ON s.id = scl.search_id " +
            "WHERE s.id = :searchId " +
            "ORDER BY scl.climb_level_id", nativeQuery = true)
    List<ISearchClimbLevelDTO> findClimbLevelsByIdSearchId(@Param("searchId") Long searchId);
}