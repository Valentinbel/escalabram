package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.exception.BadRequestAlertException;
import com.escalabram.escalabram.model.ClimbLevel;
import com.escalabram.escalabram.model.Match;
import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.repository.MatchRepository;
import com.escalabram.escalabram.repository.SearchRepository;
import com.escalabram.escalabram.service.MatchService;
import com.escalabram.escalabram.service.SearchService;
import com.escalabram.escalabram.service.dto.ISearchClimbLevelDTO;
import com.escalabram.escalabram.service.dto.SearchMatchDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private static final Logger log = LoggerFactory.getLogger(MatchServiceImpl.class);
    private final MatchRepository matchRepository;
    private final SearchRepository searchRepository;
    private final SearchService searchService;

    Set<Long> matchedSearchIds = new HashSet<>();

    @Override
    public Set<Match> createMatchesIfFit(Long searchId) {
        matchedSearchIds.clear();

        // get Search from searchId
        Optional<Search> optSearch = searchService.findById(searchId);
        if(optSearch.isEmpty())
            throw new BadRequestAlertException("This search don't exist " + searchId);
        Search search = optSearch.get();

        // Sort ClimbLevels
        List<Long> matchingClimbLevelIds = sortClimbLevelIds(search);
        log.info("matchingClimbLevelIds: {}", matchingClimbLevelIds);

        List<LocalDateTime> matchingBeginTimes = new ArrayList<>();
        search.getTimeSlots().forEach(timeSlot ->
            matchingBeginTimes.add(timeSlot.getBeginTime())
        );

        // Searches that may have matched
        List<SearchMatchDTO> searchMatchDTOs = searchRepository.findAllSearchesByCriterias(search.getProfile().getId(), search.getPlaceId(), matchingBeginTimes);
        log.info("searchMatchDTOs: {}", searchMatchDTOs);

        Set<Match> newMatches = new HashSet<>();
        if (searchMatchDTOs.isEmpty())
            return newMatches;

        // Coincide with climbLevels
        List<SearchMatchDTO> matchedClimbLevels = getMatchedClimbLevels(searchMatchDTOs, matchingClimbLevelIds);

        if (matchedClimbLevels.isEmpty())
            log.info("Some timeslots have matched but not the climbLevels");

        matchedClimbLevels.forEach(searchForMatchDTO -> {
            Optional<Match> optionalMatch = matchRepository.findByCriterias(search.getId(), searchForMatchDTO.getSearchId(), searchForMatchDTO.getTimeSlotId(), true);

            if (optionalMatch.isEmpty()) {
                Match newMatch = new Match();
                newMatch.setMatchingSearchId(search.getId());
                newMatch.setMatchedSearchId(searchForMatchDTO.getSearchId());
                newMatch.setMatchedTimeSlotId(searchForMatchDTO.getTimeSlotId());
                newMatch.setMutualMatch(true);

                log.info("newMatch to be saved: {}", newMatch);
                Match savedMatch = matchRepository.save(newMatch);
                newMatches.add(savedMatch);
            } else {
                log.info("This is a Match. However, this Match already exists in our Database: {}", optionalMatch.orElseThrow());
                newMatches.add(optionalMatch.orElseThrow());
            }
        });
        return newMatches; //TODO ajouter critères de match: preferedGenreId
    }

    private List<Long> sortClimbLevelIds(Search search) {
        List<ClimbLevel> matchingClimbLevels =  search.getClimbLevels().stream().toList();
        if (matchingClimbLevels.isEmpty())
            throw new BadRequestAlertException("The Search reference has no ClimbLevels. Search: " + search);

        List<Long> matchingClimbLevelIds = new ArrayList<>();
        matchingClimbLevelIds.add(matchingClimbLevels.getFirst().getId());
        matchingClimbLevelIds.add(matchingClimbLevels.getLast().getId());
        Collections.sort(matchingClimbLevelIds);
        return matchingClimbLevelIds;
    }

    private List<SearchMatchDTO> getMatchedClimbLevels(List<SearchMatchDTO> matchedSearches, List<Long> matchingClimbLevelIds) {
        List<SearchMatchDTO> matchedClimbLevels = new ArrayList<>();

        matchedSearches.forEach(dto -> {
            List<ISearchClimbLevelDTO> matchedClimbLevelDTOs = searchRepository.findClimbLevelsByIdSearchId(dto.getSearchId());
            List<Long> matchedClimbLevelIds = new ArrayList<>();
            matchedClimbLevelDTOs.forEach(climbLevelDTO -> {
                if (climbLevelDTO.getSearchid().equals(dto.getSearchId()))
                    matchedClimbLevelIds.add(climbLevelDTO.getClimblevelid());
            });
            Collections.sort(matchedClimbLevelIds);

            if (isClimbLevelMatching(matchedClimbLevelIds, matchingClimbLevelIds)) {
                matchedSearches.forEach(search -> {
                    if (search.getSearchId().equals(dto.getSearchId())) {
                        matchedClimbLevels.add(search);
                    }
                });
            }
        });
        return matchedClimbLevels;
    }

    private boolean isClimbLevelMatching(List<Long> matchedClimbLevelIds, List<Long> matchingClimbLevelIds) {
        return (matchedClimbLevelIds.getFirst() > matchingClimbLevelIds.getFirst()
                && matchedClimbLevelIds.getFirst() < matchingClimbLevelIds.getLast())

                || (matchedClimbLevelIds.getLast() > matchingClimbLevelIds.getFirst()
                && matchedClimbLevelIds.getLast() < matchingClimbLevelIds.getLast())

                || (matchedClimbLevelIds.getFirst().equals(matchingClimbLevelIds.getFirst())
                || matchedClimbLevelIds.getLast().equals(matchingClimbLevelIds.getLast()));
    }
}
