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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final SearchRepository searchRepository;

    Set<Long> matchedSearchIds = new HashSet<>();

    public MatchServiceImpl(MatchRepository matchRepository, SearchRepository searchRepository) {
        this.matchRepository = matchRepository;
        this.searchRepository = searchRepository;
    }

    @Override
    public List<Match> createMatchesIfExist(Search search) {
        // New search for matching (MATCHING)
        Long matchingSearchId = search.getId();
        Long matchingClimberProfile = search.getClimberProfileId();
        Long matchingPlaceId = search.getPlaceId();
        List<TimeSlot> matchingTimeSlots = search.getTimeSlots();
        List<Timestamp> matchingBeginTimes = new ArrayList<>();

        List<ClimbLevel> matchingClimbLevels = search.getClimbLevels().stream().toList();
        List<Long> matchingClimbLevelIds = new ArrayList<>();
        matchingClimbLevelIds.add(matchingClimbLevels.getFirst().getId());
        matchingClimbLevelIds.add(matchingClimbLevels.getLast().getId());
        Collections.sort(matchingClimbLevelIds);

        HashMap<Timestamp, Timestamp> timeSlotsHashMap = new HashMap<>();
        matchingTimeSlots.forEach(timeSlot -> {
            matchingBeginTimes.add(timeSlot.getBeginTime());
            timeSlotsHashMap.put(timeSlot.getBeginTime(), timeSlot.getEndTime());
        });

        // Searches that may have matched
        List<SearchMatchDTO> searchMatchDTOs = searchRepository.findSearchesByCriterias(matchingClimberProfile, matchingPlaceId, matchingBeginTimes);

        List<Match> newMatchList = new ArrayList<>();
        if(!searchMatchDTOs.isEmpty()) {
            // coincide with timeSlots
            List<SearchMatchDTO> matchedTimeSlots = getMatchedTimeSlots(searchMatchDTOs, timeSlotsHashMap);

            // Coincide with climbLevels
            if(!matchedTimeSlots.isEmpty()) {
                List<SearchMatchDTO> matchedClimbLevels = getMatchedClimbLevels(matchedTimeSlots, matchingClimbLevelIds);

                matchedClimbLevels.forEach(searchForMatchDTO -> {
                    Optional<Match> optionalMatch = matchRepository.findByAllCriterias(matchingSearchId, searchForMatchDTO.getSearchId(), searchForMatchDTO.getTimeSlotId(), true );
                    if(optionalMatch.isEmpty()) {
                        Match newMatch = new Match();
                        newMatch.setMatchingSearchId(matchingSearchId);
                        newMatch.setMatchedSearchId(searchForMatchDTO.getSearchId());
                        newMatch.setMatchedTimeSlotId(searchForMatchDTO.getTimeSlotId());
                        newMatch.setMutualMatch(true);
                        matchRepository.save(newMatch);
                        newMatchList.add(newMatch);
                    } else {
                        System.out.println("This is a Match. This Match already exists in our Database: " + optionalMatch.get());
                        newMatchList.add(optionalMatch.get());
                    }
                });
            }
        }
        return newMatchList;
        //TODO ajouter critères de match: preferedGenreId
    }

    private List<SearchMatchDTO> getMatchedTimeSlots(List<SearchMatchDTO> searchMatchDTOs, HashMap<Timestamp, Timestamp> timeSlotsHashMap){
        List<SearchMatchDTO> matchedTimeSlots = new ArrayList<>();
        searchMatchDTOs.forEach(searchMatchDTO ->
            timeSlotsHashMap.forEach((Timestamp begin, Timestamp end) ->{
                if (isTimeSlotMatching(begin, end, searchMatchDTO)){
                    System.out.println("TIMESLOT MATCH: " + searchMatchDTO );
                    matchedTimeSlots.add(searchMatchDTO);
                    matchedSearchIds.add(searchMatchDTO.getSearchId());
                }
            }));
        return matchedTimeSlots;
    }

    private boolean isTimeSlotMatching(Timestamp begin, Timestamp end, SearchMatchDTO searchMatchDTO){
        return (begin.toInstant().isBefore(searchMatchDTO.getBeginTime().toInstant())
                    && begin.toInstant().isAfter(searchMatchDTO.getEndTime().toInstant()))

                || (end.toInstant().isBefore(searchMatchDTO.getBeginTime().toInstant())
                    && end.toInstant().isAfter(searchMatchDTO.getEndTime().toInstant()))

                || (begin.toInstant().equals(searchMatchDTO.getBeginTime().toInstant()))
                    || (end.toInstant().equals(searchMatchDTO.getEndTime().toInstant()));
    }

    private List<SearchMatchDTO> getMatchedClimbLevels(List<SearchMatchDTO> matchedSearches, List<Long> matchingClimbLevelIds){
        List<SearchMatchDTO> matchedClimbLevels = new ArrayList<>();

        matchedSearchIds.forEach(searchId -> {
            List<ISearchClimbLevelDTO> matchedClimbLevelDTOs = searchRepository.findClimbLevelsByIdSearchId(searchId);
            List<Long> matchedClimbLevelIds = new ArrayList<>();
            matchedClimbLevelDTOs.forEach(climbLevelDTO -> {
                if(climbLevelDTO.getSearchid().equals(searchId))
                    matchedClimbLevelIds.add(climbLevelDTO.getClimblevelid());
            });
            Collections.sort(matchedClimbLevelIds);

            if(isClimbLevelMatching(matchedClimbLevelIds, matchingClimbLevelIds)){
                matchedSearches.forEach(search -> {
                    if(search.getSearchId().equals(searchId)){
                        matchedClimbLevels.add(search);
                    }
                });
            }
        });
        return matchedClimbLevels;
    }

    private boolean isClimbLevelMatching(List<Long> matchedClimbLevelIds, List<Long> matchingClimbLevelIds){
        return (matchedClimbLevelIds.getFirst() > matchingClimbLevelIds.getFirst()
                    && matchedClimbLevelIds.getFirst() < matchingClimbLevelIds.getLast())

                || (matchedClimbLevelIds.getLast() > matchingClimbLevelIds.getFirst()
                    && matchedClimbLevelIds.getLast() < matchingClimbLevelIds.getLast())

                || (matchedClimbLevelIds.getFirst().equals(matchingClimbLevelIds.getFirst())
                    || matchedClimbLevelIds.getLast().equals(matchingClimbLevelIds.getLast()));
    }
}
