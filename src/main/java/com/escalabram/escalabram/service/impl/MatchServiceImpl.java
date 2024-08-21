package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimbLevel;
import com.escalabram.escalabram.model.Match;
import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.model.TimeSlot;
import com.escalabram.escalabram.repository.MatchRepository;
import com.escalabram.escalabram.repository.SearchRepository;
import com.escalabram.escalabram.service.MatchService;
import com.escalabram.escalabram.service.dto.ISearchClimbLevelDTO;
import com.escalabram.escalabram.service.dto.SearchForMatchDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final SearchRepository searchRepository;

    Set<Long> searchIdList = new HashSet<>();

    public MatchServiceImpl(MatchRepository matchRepository, SearchRepository searchRepository) {
        this.matchRepository = matchRepository;
        this.searchRepository = searchRepository;
    }

    @Override
    public List<Match> createMatchesIfExist(Search search) {
        // New search for matching
        // Vérifier que la query ne pete pas meme si timeSlotList est vide
        Long matchingSearchId = search.getId();
        Long searchClimberProfile = search.getClimberProfileId();
        Long searchPlaceId = search.getPlaceId();
        List<TimeSlot> timeSlotList = search.getTimeSlots();
        List<Timestamp> beginTime = new ArrayList<>();

        HashMap<Timestamp, Timestamp> beginAndEndTimeSlots = new HashMap<>();
        timeSlotList.forEach(eachTimeSlot -> {
            beginTime.add(eachTimeSlot.getBeginTime());
            beginAndEndTimeSlots.put(eachTimeSlot.getBeginTime(), eachTimeSlot.getEndTime());
        });

        // Searches that may have matched
        List<SearchForMatchDTO> searchForMatchDTOList =
                matchRepository.findSearchesByMatchCriterias(searchClimberProfile, searchPlaceId, beginTime);

        List<Match> newMatchList = new ArrayList<>();
        if(!searchForMatchDTOList.isEmpty()) {
            List<SearchForMatchDTO> matchedOnes = new ArrayList<>();
            searchForMatchDTOList.forEach(searchForMatchDTO ->
                    beginAndEndTimeSlots.forEach((Timestamp begin, Timestamp end) ->{

                if (isTimeSlotMatching(begin, end, searchForMatchDTO)){
                    System.out.println("IS IT A MATCH ? " + searchForMatchDTO );
                    matchedOnes.add(searchForMatchDTO);
                }
            }));

            // TODO ajouter vérif sur les climbLevel

            matchedOnes.forEach(searchForMatchDTO -> {
                Match newMatch = new Match();
                newMatch.setMatchingSearchId(matchingSearchId);
                newMatch.setMatchedSearchId(searchForMatchDTO.getSearchId());
                newMatch.setMatchedTimeSlotId(searchForMatchDTO.getTimeSlotId());
                newMatch.setMutualMatch(true);
                matchRepository.save(newMatch); // mettre une condition pour ajouter que si existe pas.
            });
        }
        return newMatchList;
        //TODO ajouter critères de match: preferedGenreId, climbLevelList
    }

    private boolean isTimeSlotMatching(Timestamp begin, Timestamp end, SearchForMatchDTO searchForMatchDTO){
        return (begin.toInstant().isBefore(searchForMatchDTO.getBeginTime().toInstant())
                && begin.toInstant().isAfter(searchForMatchDTO.getEndTime().toInstant()))
                || (end.toInstant().isBefore(searchForMatchDTO.getBeginTime().toInstant())
                && end.toInstant().isAfter(searchForMatchDTO.getEndTime().toInstant()))
                || (begin.toInstant().equals(searchForMatchDTO.getBeginTime().toInstant()))
                || (end.toInstant().equals(searchForMatchDTO.getEndTime().toInstant()));
    }
}
