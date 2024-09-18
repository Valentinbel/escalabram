package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimbLevel;
import com.escalabram.escalabram.model.Match;
import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.model.TimeSlot;
import com.escalabram.escalabram.repository.MatchRepository;
import com.escalabram.escalabram.repository.SearchRepository;
import com.escalabram.escalabram.service.MatchService;
import com.escalabram.escalabram.service.dto.ISearchClimbLevelDTO;
import com.escalabram.escalabram.service.dto.SearchMatchDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class MatchServiceImpl implements MatchService {
    private static final Logger log = LoggerFactory.getLogger(MatchServiceImpl.class);
    Set<Long> matchedSearchIds = new HashSet<>();

    private final MatchRepository matchRepository;
    private final SearchRepository searchRepository;

    public MatchServiceImpl(MatchRepository matchRepository, SearchRepository searchRepository) {
        this.matchRepository = matchRepository;
        this.searchRepository = searchRepository;
    }

    @Override
    public List<Match> createMatchesIfFit(Search search) {
        matchedSearchIds.clear();
        // New search for matching (MATCHING)
        Long matchingSearchId = search.getId();
        Long matchingClimberProfile = search.getClimberProfileId();
        Long matchingPlaceId = search.getPlaceId();
        Set<TimeSlot> matchingTimeSlots = search.getTimeSlots();
        List<Timestamp> matchingBeginTimes = new ArrayList<>();

        List<ClimbLevel> matchingClimbLevels = search.getClimbLevels().stream().toList();

        List<Match> newMatches = new ArrayList<>();
        if (!matchingClimbLevels.isEmpty()) {
            List<Long> matchingClimbLevelIds = new ArrayList<>();
            matchingClimbLevelIds.add(matchingClimbLevels.getFirst().getId());
            matchingClimbLevelIds.add(matchingClimbLevels.getLast().getId());
            Collections.sort(matchingClimbLevelIds);

            HashMap<Timestamp, Timestamp> timeSlotsHashMap = new HashMap<>(); // Remplacer par un SearchMatchDTO
            matchingTimeSlots.forEach(timeSlot -> {
                matchingBeginTimes.add(timeSlot.getBeginTime());
                timeSlotsHashMap.put(timeSlot.getBeginTime(), timeSlot.getEndTime());
            });

            // Searches that may have matched
            List<SearchMatchDTO> searchMatchDTOs = searchRepository.findAllSearchesByCriterias(matchingClimberProfile, matchingPlaceId, matchingBeginTimes);

            if (!searchMatchDTOs.isEmpty()) {
                // coincide with timeSlots
                List<SearchMatchDTO> matchedTimeSlots = getMatchedTimeSlots(searchMatchDTOs, timeSlotsHashMap);

                // Coincide with climbLevels
                if (!matchedTimeSlots.isEmpty()) {
                    List<SearchMatchDTO> matchedClimbLevels = getMatchedClimbLevels(matchedTimeSlots, matchingClimbLevelIds);

                    if (matchedClimbLevels.isEmpty())
                        log.info("Some timeslots have matched but not the climbLevels");

                    matchedClimbLevels.forEach(searchForMatchDTO -> {
                        Optional<Match> optionalMatch = matchRepository.findByCriterias(matchingSearchId, searchForMatchDTO.getSearchId(), searchForMatchDTO.getTimeSlotId(), true);

                        if (optionalMatch.isEmpty()) {
                            Match newMatch = new Match();
                            newMatch.setMatchingSearchId(matchingSearchId);
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
                } else log.info("There are no matchedTimeSlots. We can't say about ClimbLevels");
            }
        } else log.error("The Search reference has no ClimbLevels. Search : {}", search);
        return newMatches;
        //TODO ajouter critères de match: preferedGenreId
        // TODO refactor to pass cognitive complexity
    }

    private List<SearchMatchDTO> getMatchedTimeSlots(List<SearchMatchDTO> searchMatchDTOs, HashMap<Timestamp, Timestamp> timeSlotsHashMap) {
        List<SearchMatchDTO> matchedTimeSlots = new ArrayList<>();
        searchMatchDTOs.forEach(searchMatchDTO ->
                timeSlotsHashMap.forEach((Timestamp begin, Timestamp end) -> {
                    if (isTimeSlotMatching(begin, end, searchMatchDTO)) {
                        log.info("begin: {}. end: {}. searchMatchDTO: {}", begin, end, searchMatchDTO);
                        matchedTimeSlots.add(searchMatchDTO);
                        matchedSearchIds.add(searchMatchDTO.getSearchId());
                    }
                }));
        return matchedTimeSlots;
    }

    private boolean isTimeSlotMatching(Timestamp begin, Timestamp end, SearchMatchDTO searchMatchDTO) {
        return (begin.toInstant().isAfter(searchMatchDTO.getBeginTime().toInstant())
                && begin.toInstant().isBefore(searchMatchDTO.getEndTime().toInstant()))

                || (end.toInstant().isAfter(searchMatchDTO.getBeginTime().toInstant())
                && end.toInstant().isBefore(searchMatchDTO.getEndTime().toInstant()))

                || (begin.toInstant().equals(searchMatchDTO.getBeginTime().toInstant()))
                || (end.toInstant().equals(searchMatchDTO.getEndTime().toInstant()));
    }

    private List<SearchMatchDTO> getMatchedClimbLevels(List<SearchMatchDTO> matchedSearches, List<Long> matchingClimbLevelIds) {
        List<SearchMatchDTO> matchedClimbLevels = new ArrayList<>();

        matchedSearchIds.forEach(searchId -> {
            List<ISearchClimbLevelDTO> matchedClimbLevelDTOs = searchRepository.findClimbLevelsByIdSearchId(searchId);
            List<Long> matchedClimbLevelIds = new ArrayList<>();
            matchedClimbLevelDTOs.forEach(climbLevelDTO -> {
                if (climbLevelDTO.getSearchid().equals(searchId))
                    matchedClimbLevelIds.add(climbLevelDTO.getClimblevelid());
            });
            Collections.sort(matchedClimbLevelIds);

            if (isClimbLevelMatching(matchedClimbLevelIds, matchingClimbLevelIds)) {
                matchedSearches.forEach(search -> {
                    if (search.getSearchId().equals(searchId)) {
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
