package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.Match;
import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.model.TimeSlot;
import com.escalabram.escalabram.repository.MatchRepository;
import com.escalabram.escalabram.service.MatchService;
import com.escalabram.escalabram.service.dto.SearchForMatchDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;

    public MatchServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
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
        List<Timestamp> endTime = new ArrayList<>();
        List<Date> dateList = new ArrayList<>();
        timeSlotList.forEach(eachTimeSlot -> {
            beginTime.add(eachTimeSlot.getBeginTime());
            endTime.add(eachTimeSlot.getEndTime());
           // dateList.add(eachTimeSlot.getBeginTime().getTime());
        });

        // Searches that may have matched


        // ajouter begin time.date.
        List<SearchForMatchDTO> setSearchMatchedId = matchRepository.findSearchesByMatchCriterias(searchClimberProfile, searchPlaceId);
        List<Match> newMatchList = new ArrayList<>();
        if(!setSearchMatchedId.isEmpty()) {


            // TimeSlots
//            List<String> listOfCommonItems = listOne.stream()
//                    .filter(item -> listTwo.contains(item))
//                    .toList();

            setSearchMatchedId.forEach(eachSearchMatchId -> {
                Match newMatch = new Match();
                newMatch.setMatchingSearchId(matchingSearchId);
                newMatch.setMatchedSearchId(eachSearchMatchId.getSearchId());
                newMatch.setMutualMatch(true);
                newMatch.setMatchDate(LocalDate.now());
                matchRepository.save(newMatch);
            });
        }
        return newMatchList;
        //TODO ajouter critères de match: preferedGenreId, climbLevelList
    }
}
