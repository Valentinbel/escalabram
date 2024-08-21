package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.service.dto.ISearchClimbLevelDTO;
import com.escalabram.escalabram.service.dto.SearchForMatchDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.*;

public interface SearchRepository extends JpaRepository<Search, Long> {

    Optional<Set<Search>> findByClimberProfileId(Long climberProfileId);

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

    @Query(value ="SELECT DISTINCT s.id as searchid, scl.climb_level_id as climblevelid " + // @Query(value ="SELECT s.id as searchid, scl.climb_level_id as climblevelid " +
            "FROM Search s " +
            "INNER JOIN search_climb_level scl ON s.id = scl.search_id " +
            "WHERE s.id = :searchIdList " + //IN :searchIdList
            "ORDER BY scl.climb_level_id", nativeQuery = true)
    List<ISearchClimbLevelDTO> findClimbLevelsByIdSearchId(@Param("searchIdList") Long searchIdList); //Set<Long>
}