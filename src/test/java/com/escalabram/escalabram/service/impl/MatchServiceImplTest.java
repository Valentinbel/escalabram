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
import org.junit.jupiter.api.BeforeAll;
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
    public static List<Search> searches;
    public static Set<ClimbLevel> climbLevelsMatching1;
    public static Set<ClimbLevel> climbLevelsMatching2;
    public static Set<ClimbLevel> climbLevelsNOTMatching;
    public static Set<TimeSlot> timeSlotsMatching1;
    public static Set<TimeSlot> timeSlotsMatching2;
    public static Set<TimeSlot> timeSlotsNOTMatching;

    @InjectMocks
    private MatchServiceImpl matchServiceImpl;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private SearchRepository searchRepository;

    @BeforeAll
    public static void init() {
        climbLevelsMatching1 = Stream.of(
                new ClimbLevel(2L ,"4+"),
                new ClimbLevel(7L ,"6A+")
        ).collect(Collectors.toSet());

        climbLevelsMatching2 = Stream.of(
                new ClimbLevel(6L ,"6A"),
                new ClimbLevel(9L ,"6B+")
        ).collect(Collectors.toSet());
        climbLevelsNOTMatching = Stream.of(
                new ClimbLevel(9L ,"6B+"),
                new ClimbLevel(12L ,"7A")
        ).collect(Collectors.toSet());

        String beginTime1 = "2024-09-02 13:59:59.123456789";
        String endTime1 = "2024-09-02 18:59:59.123456789";

        timeSlotsMatching1 = Stream.of(
                new TimeSlot(1L, Timestamp.valueOf(beginTime1), Timestamp.valueOf(endTime1))).collect(Collectors.toSet());

        String beginTime2 = "2024-09-02 15:59:59.123456789";
        String endTime2 = "2024-09-02 19:59:59.123456789";

        timeSlotsMatching2 = Stream.of(
                new TimeSlot(3L, Timestamp.valueOf(beginTime2), Timestamp.valueOf(endTime2))).collect(Collectors.toSet());

        String beginTime3 = "2024-09-02 22:59:59.123456789";
        String endTime3 = "2024-09-02 23:59:59.123456789";

        String beginTime4 = "2024-09-03 04:00:00.123456789";
        String endTime4 = "2024-09-03 06:00:00.123456789";

        timeSlotsNOTMatching = Stream.of(
                new TimeSlot(5L, Timestamp.valueOf(beginTime3), Timestamp.valueOf(endTime3)),
                new TimeSlot(6L, Timestamp.valueOf(beginTime4), Timestamp.valueOf(endTime4))
        ).collect(Collectors.toSet());

        searches = Stream.of(
                new Search(1L,1L, "search1Profile1", true, true, true,
                        true, 1L,1L, climbLevelsMatching1, true),
                new Search(2L,1L, "search2Profile1", true, true, true,
                        true, 2L,1L, climbLevelsMatching1, true),
                new Search(3L,2L, "search1Profile2", true, true, true,
                        true, 2L,1L, climbLevelsMatching2, true),
                new Search(4L,4L, "search1Profile3", true, true, true,
                        true, 2L,1L, climbLevelsNOTMatching, true)
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
        verify(matchRepository, times(1)).findByCriterias(match.getMatchingSearchId(), match.getMatchedSearchId(),match.getMatchedTimeSlotId(), match.isMutualMatch());
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

        when(matchRepository.findByCriterias(match.getMatchingSearchId(), match.getMatchedSearchId(),match.getMatchedTimeSlotId(), match.isMutualMatch()))
                .thenReturn(Optional.of(match));

        List<Match> matches = matchServiceImpl.createMatchesIfFit(searchMatching);

        verify(searchRepository, times(1)).findAllSearchesByCriterias(searchMatching.getClimberProfileId(), searchMatching.getPlaceId(), matchingBeginTimesSearchMatching);
        verify(searchRepository, times(1)).findClimbLevelsByIdSearchId(searchToMatched.getId());
        verify(matchRepository, times(1)).findByCriterias(match.getMatchingSearchId(), match.getMatchedSearchId(),match.getMatchedTimeSlotId(), match.isMutualMatch());

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