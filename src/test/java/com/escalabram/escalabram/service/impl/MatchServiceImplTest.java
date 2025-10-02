package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimbLevel;
import com.escalabram.escalabram.model.Match;
import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.model.TimeSlot;
import com.escalabram.escalabram.repository.MatchRepository;
import com.escalabram.escalabram.repository.SearchRepository;
import com.escalabram.escalabram.service.dto.ISearchClimbLevelDTO;
import com.escalabram.escalabram.service.dto.SearchClimbLevelDTO;
import com.escalabram.escalabram.service.dto.SearchMatchDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceImplTest {

    @InjectMocks
    private MatchServiceImpl matchServiceImpl;
    @Mock
    private MatchRepository matchRepository;
    @Mock
    private SearchRepository searchRepository;

    private List<Search> searches;
    private Set<TimeSlot> timeSlotsMatching1;
    private Set<TimeSlot> timeSlotsMatching2;
    private Set<TimeSlot> timeSlotsNOTMatching;

    @BeforeEach
    void setupData() {

        Set<ClimbLevel> climbLevelsMatching1 = Stream.of(
                ClimbLevel.builder().id(2L).codeFr("4+").build(),
                ClimbLevel.builder().id(7L).codeFr("6A+").build()
        ).collect(Collectors.toSet());

        Set<ClimbLevel> climbLevelsMatching2 = Stream.of(
                ClimbLevel.builder().id(6L).codeFr("6A").build(),
                ClimbLevel.builder().id(9L).codeFr("6B+").build()
        ).collect(Collectors.toSet());
        Set<ClimbLevel> climbLevelsNOTMatching = Stream.of(
                ClimbLevel.builder().id(9L).codeFr("6B+").build(),
                ClimbLevel.builder().id(12L).codeFr("7A").build()
        ).collect(Collectors.toSet());

        String beginTime1 = "2024-09-02 13:59:59.123456789";
        String endTime1 = "2024-09-02 18:59:59.123456789";

        timeSlotsMatching1 = Stream.of(
                TimeSlot.builder()
                        .id(1L)
                        .beginTime(Timestamp.valueOf(beginTime1))
                        .endTime(Timestamp.valueOf(endTime1))
                        .build()
    ).collect(Collectors.toSet());

        String beginTime2 = "2024-09-02 15:59:59.123456789";
        String endTime2 = "2024-09-02 19:59:59.123456789";

        timeSlotsMatching2 = Stream.of(
                TimeSlot.builder()
                        .id(3L)
                        .beginTime(Timestamp.valueOf(beginTime2))
                        .endTime(Timestamp.valueOf(endTime2))
                        .build()
        ).collect(Collectors.toSet());

        String beginTime3 = "2024-09-02 22:59:59.123456789";
        String endTime3 = "2024-09-02 23:59:59.123456789";

        String beginTime4 = "2024-09-03 04:00:00.123456789";
        String endTime4 = "2024-09-03 06:00:00.123456789";

        timeSlotsNOTMatching = Stream.of(
                TimeSlot.builder()
                        .id(5L)
                        .beginTime(Timestamp.valueOf(beginTime3))
                        .endTime(Timestamp.valueOf(endTime3))
                        .build(),
                TimeSlot.builder()
                        .id(6L)
                        .beginTime(Timestamp.valueOf(beginTime4))
                        .endTime(Timestamp.valueOf(endTime4))
                        .build()
        ).collect(Collectors.toSet());

        searches = Stream.of(
                Search.builder()
                        .id(1L)
                        .climberProfileId(1L)
                        .title("search1Profile1")
                        .haveRope(true)
                        .haveBelayDevice(true)
                        .haveQuickdraw(true)
                        .haveCarToShare(true)
                        .placeId(1L)
                        .preferedGenderId(1L)
                        .climbLevels(climbLevelsMatching1)
                        .isActive(true)
                        .build(),
                Search.builder()
                        .id(2L)
                        .climberProfileId(1L)
                        .title("search2Profile1")
                        .haveRope(true)
                        .haveBelayDevice(true)
                        .haveQuickdraw(true)
                        .haveCarToShare(true)
                        .placeId(2L)
                        .preferedGenderId(1L)
                        .climbLevels(climbLevelsMatching1)
                        .isActive(true)
                        .build(),
                Search.builder()
                        .id(3L)
                        .climberProfileId(2L)
                        .title("search1Profile2")
                        .haveRope(true)
                        .haveBelayDevice(true)
                        .haveQuickdraw(true)
                        .haveCarToShare(true)
                        .placeId(2L)
                        .preferedGenderId(1L)
                        .climbLevels(climbLevelsMatching2)
                        .isActive(true)
                        .build(),
                Search.builder()
                        .id(4L)
                        .climberProfileId(4L)
                        .title("search1Profile3")
                        .haveRope(true)
                        .haveBelayDevice(true)
                        .haveQuickdraw(true)
                        .haveCarToShare(true)
                        .placeId(2L)
                        .preferedGenderId(1L)
                        .climbLevels(climbLevelsNOTMatching)
                        .isActive(true)
                        .build()
        ).toList();
    }

    @Test
    void createMatchesIfFit_matching(){
        // Matching
        Search searchMatching = searches.getFirst();
        searchMatching.setTimeSlots(timeSlotsMatching1);
        List<Timestamp> matchingBeginTimesSearchMatching =  new ArrayList<>();
        matchingBeginTimesSearchMatching.add(timeSlotsMatching1.stream().toList().getFirst().getBeginTime());

        // That may have matched
        Search searchToMatched = searches.get(2);
        searchToMatched.setTimeSlots(timeSlotsMatching2);

        SearchMatchDTO searchMatchDTO1 = new SearchMatchDTO(searches.get(2).getId(),
                timeSlotsMatching2.stream().toList().getFirst().getId(),
                timeSlotsMatching2.stream().toList().getFirst().getBeginTime(),
                timeSlotsMatching2.stream().toList().getFirst().getEndTime());

        List<ISearchClimbLevelDTO> matchedClimbLevelDTOs = new ArrayList<>();
        SearchClimbLevelDTO searchClimbLevelDTO1 = new SearchClimbLevelDTO(searchToMatched.getId(),6L);
        SearchClimbLevelDTO searchClimbLevelDTO2 = new SearchClimbLevelDTO(searchToMatched.getId(),9L);
        matchedClimbLevelDTOs.add(searchClimbLevelDTO1);
        matchedClimbLevelDTOs.add(searchClimbLevelDTO2);

        Match match = new Match(1L, searches.getFirst().getId(), searchMatchDTO1.getSearchId(), searchMatchDTO1.getTimeSlotId(), true);
        Optional<Match> emptyMatch = Optional.empty();
        List<Match> matchesToResult = new ArrayList<>();
        matchesToResult.add(match);

        List<SearchMatchDTO> searchMatchDTOs = new ArrayList<>();
        searchMatchDTOs.add(searchMatchDTO1);

        when(searchRepository.findAllSearchesByCriterias(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(searchMatchDTOs);
        when(searchRepository.findClimbLevelsByIdSearchId(ArgumentMatchers.any())).thenReturn(matchedClimbLevelDTOs);
        when(matchRepository.findByCriterias(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
                .thenReturn(emptyMatch);
        when(matchRepository.save(ArgumentMatchers.any())).thenReturn(match);

        List<Match> matches = matchServiceImpl.createMatchesIfFit(searchMatching);

        verify(searchRepository, times(1)).findAllSearchesByCriterias(searchMatching.getClimberProfileId(), searchMatching.getPlaceId(), matchingBeginTimesSearchMatching);
        verify(searchRepository, times(1)).findClimbLevelsByIdSearchId(searchToMatched.getId());
        verify(matchRepository, times(1)).findByCriterias(match.getMatchingSearchId(), match.getMatchedSearchId(),match.getMatchedTimeSlotId(), match.getMutualMatch());
        verify(matchRepository, times(1)).save(ArgumentMatchers.any());

        assertEquals(matches, matchesToResult);
    }

    @Test
    void createMatchesIfFit_matchAlreadyExists(){
        // Matching
        Search searchMatching = searches.getFirst();
        searchMatching.setTimeSlots(timeSlotsMatching1);
        List<Timestamp> matchingBeginTimesSearchMatching =  new ArrayList<>();
        matchingBeginTimesSearchMatching.add(timeSlotsMatching1.stream().toList().getFirst().getBeginTime());

        // That may have matched
        Search searchToMatched = searches.get(2);
        searchToMatched.setTimeSlots(timeSlotsMatching2);

        SearchMatchDTO searchMatchDTO1 = new SearchMatchDTO(searches.get(2).getId(),
                timeSlotsMatching2.stream().toList().getFirst().getId(),
                timeSlotsMatching2.stream().toList().getFirst().getBeginTime(),
                timeSlotsMatching2.stream().toList().getFirst().getEndTime());

        List<ISearchClimbLevelDTO> matchedClimbLevelDTOs = new ArrayList<>();
        SearchClimbLevelDTO searchClimbLevelDTO1 = new SearchClimbLevelDTO(searchToMatched.getId(),6L);
        SearchClimbLevelDTO searchClimbLevelDTO2 = new SearchClimbLevelDTO(searchToMatched.getId(),9L);
        matchedClimbLevelDTOs.add(searchClimbLevelDTO1);
        matchedClimbLevelDTOs.add(searchClimbLevelDTO2);

        Match match = new Match(1L, searches.getFirst().getId(), searchMatchDTO1.getSearchId(), searchMatchDTO1.getTimeSlotId(), true);
        List<Match> matchesToResult = new ArrayList<>();
        matchesToResult.add(match);

        List<SearchMatchDTO> searchMatchDTOs = new ArrayList<>();
        searchMatchDTOs.add(searchMatchDTO1);


        when(searchRepository.findAllSearchesByCriterias(searchMatching.getClimberProfileId(), searchMatching.getPlaceId(), matchingBeginTimesSearchMatching))
                .thenReturn(searchMatchDTOs);

        when(searchRepository.findClimbLevelsByIdSearchId(searchToMatched.getId())).thenReturn(matchedClimbLevelDTOs);

        when(matchRepository.findByCriterias(match.getMatchingSearchId(), match.getMatchedSearchId(),match.getMatchedTimeSlotId(), match.getMutualMatch()))
                .thenReturn(Optional.of(match));

        List<Match> matches = matchServiceImpl.createMatchesIfFit(searchMatching);

        verify(searchRepository, times(1)).findAllSearchesByCriterias(searchMatching.getClimberProfileId(), searchMatching.getPlaceId(), matchingBeginTimesSearchMatching);
        verify(searchRepository, times(1)).findClimbLevelsByIdSearchId(searchToMatched.getId());
        verify(matchRepository, times(1)).findByCriterias(match.getMatchingSearchId(), match.getMatchedSearchId(),match.getMatchedTimeSlotId(), match.getMutualMatch());

        assertEquals(matches, matchesToResult);
    }

    @Test
    void createMatchesIfFit_timeSlotsNOMatch(){
        // Matching
        Search searchMatching = searches.getFirst();
        searchMatching.setTimeSlots(timeSlotsMatching1);
        List<Timestamp> matchingBeginTimesSearchMatching =  new ArrayList<>();
        matchingBeginTimesSearchMatching.add(timeSlotsMatching1.stream().toList().getFirst().getBeginTime());

        // That may have matched
        Search searchToMatched = searches.get(2);
        searchToMatched.setTimeSlots(timeSlotsNOTMatching);

        SearchMatchDTO searchMatchDTO1 = new SearchMatchDTO(searches.get(2).getId(),
                timeSlotsNOTMatching.stream().toList().getFirst().getId(),
                timeSlotsNOTMatching.stream().toList().getFirst().getBeginTime(),
                timeSlotsNOTMatching.stream().toList().getFirst().getEndTime());

        List<Match> matchesToResult = new ArrayList<>();

        List<SearchMatchDTO> searchMatchDTOs = new ArrayList<>();
        searchMatchDTOs.add(searchMatchDTO1);

        when(searchRepository.findAllSearchesByCriterias(searchMatching.getClimberProfileId(), searchMatching.getPlaceId(), matchingBeginTimesSearchMatching))
                .thenReturn(searchMatchDTOs);

        List<Match> matches = matchServiceImpl.createMatchesIfFit(searchMatching);

        verify(searchRepository, times(1)).findAllSearchesByCriterias(searchMatching.getClimberProfileId(), searchMatching.getPlaceId(), matchingBeginTimesSearchMatching);

        assertEquals(matches, matchesToResult);
    }

    @Test
    void testCreateMatchesIfFit_timeSlotsMatchButClimbLevelDONT(){
        // Matching
        Search searchMatching = searches.getFirst();
        searchMatching.setTimeSlots(timeSlotsMatching1);
        List<Timestamp> matchingBeginTimesSearchMatching =  new ArrayList<>();
        matchingBeginTimesSearchMatching.add(timeSlotsMatching1.stream().toList().getFirst().getBeginTime());

        // That may have matched
        Search searchToMatched = searches.get(3);
        searchToMatched.setTimeSlots(timeSlotsMatching2);

        SearchMatchDTO searchMatchDTO1 = new SearchMatchDTO(searches.get(3).getId(),
                timeSlotsMatching2.stream().toList().getFirst().getId(),
                timeSlotsMatching2.stream().toList().getFirst().getBeginTime(),
                timeSlotsMatching2.stream().toList().getFirst().getEndTime());

        List<ISearchClimbLevelDTO> matchedClimbLevelDTOs = new ArrayList<>();
        SearchClimbLevelDTO searchClimbLevelDTO1 = new SearchClimbLevelDTO(searchToMatched.getId(),9L);
        SearchClimbLevelDTO searchClimbLevelDTO2 = new SearchClimbLevelDTO(searchToMatched.getId(),12L);
        matchedClimbLevelDTOs.add(searchClimbLevelDTO1);
        matchedClimbLevelDTOs.add(searchClimbLevelDTO2);

        List<Match> matchesToResult = new ArrayList<>();

        List<SearchMatchDTO> searchMatchDTOs = new ArrayList<>();
        searchMatchDTOs.add(searchMatchDTO1);

        when(searchRepository.findAllSearchesByCriterias(searchMatching.getClimberProfileId(), searchMatching.getPlaceId(), matchingBeginTimesSearchMatching))
                .thenReturn(searchMatchDTOs);
        when(searchRepository.findClimbLevelsByIdSearchId(searchToMatched.getId())).thenReturn(matchedClimbLevelDTOs);

        List<Match> matches = matchServiceImpl.createMatchesIfFit(searchMatching);

        verify(searchRepository, times(1)).findAllSearchesByCriterias(searchMatching.getClimberProfileId(), searchMatching.getPlaceId(), matchingBeginTimesSearchMatching);
        verify(searchRepository, times(1)).findClimbLevelsByIdSearchId(searchToMatched.getId());

        assertEquals(matches, matchesToResult);
    }
}