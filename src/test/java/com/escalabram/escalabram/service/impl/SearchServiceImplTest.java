package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimbLevel;
import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.model.TimeSlot;
import com.escalabram.escalabram.repository.SearchRepository;
import com.escalabram.escalabram.service.ClimbLevelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceImplTest {

    @InjectMocks
    private SearchServiceImpl searchServiceImpl;
    @Mock
    private ClimbLevelService climbLevelService;
    @Mock
    private SearchRepository searchRepository;

    private Set<ClimbLevel> climbLevels;
    private Set<TimeSlot> timeSlots;
    private Set<TimeSlot> timeSlot2;
    private List<Search> searches;

    @BeforeEach
    void setupData() {
        climbLevels = Stream.of(
                ClimbLevel.builder().id(2L).codeFr("4+").build(),
                ClimbLevel.builder().id(7L).codeFr("6A+").build()
        ).collect(Collectors.toSet());

        String beginTime1 = "2024-09-02 13:59:59.123456789";
        String endTime1 = "2024-09-02 18:59:59.123456789";

        String beginTime2 = "2024-09-03 19:00:00.123456789";
        String endTime2 = "2024-09-03 21:00:00.123456789";

        TimeSlot timeslot1 = TimeSlot.builder()
                .id(1L)
                .beginTime(Timestamp.valueOf(beginTime1))
                .endTime(Timestamp.valueOf(endTime1))
                .build();
        TimeSlot timeslot2 = TimeSlot.builder()
                .id(2L)
                .beginTime(Timestamp.valueOf(beginTime2))
                .endTime(Timestamp.valueOf(endTime2))
                .build();
        timeSlots = Stream.of(timeslot1, timeslot2).collect(Collectors.toSet());

        timeSlot2 = Stream.of(timeslot2).collect(Collectors.toSet());
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
                        .climbLevels(climbLevels)
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
                        .climbLevels(climbLevels)
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
                        .climbLevels(climbLevels)
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
                        .climbLevels(climbLevels)
                        .isActive(true)
                        .build()
        ).toList();

        searches.forEach(search -> search.setTimeSlots(timeSlots));
    }

    @Test
    void findAll_returnAll(){
        when(searchRepository.findAll()).thenReturn(searches);
        searches.get(3).setTimeSlots(timeSlots);

        List<Search> findedSearches = searchServiceImpl.findAll();

        assertFalse(searches.isEmpty());
        verify(searchRepository, times(1)).findAll();
        assertEquals(findedSearches.size(), searches.size());
        assertEquals(findedSearches.get(3), searches.get(3));
        assertEquals(findedSearches.get(3).getTimeSlots(), searches.get(3).getTimeSlots());
        assertEquals(findedSearches.get(0).getClimbLevels(), searches.get(0).getClimbLevels());
        assertEquals(findedSearches.get(3).getId(), searches.get(3).getId());
        assertEquals(findedSearches.get(2).getPlaceId(), searches.get(2).getPlaceId());
        assertEquals(findedSearches.get(2).getTimeSlots(), searches.get(2).getTimeSlots());
    }

    @Test
    void findById_searchId_optionalEntity() {
        when(searchRepository.findById(searches.getFirst().getId())).thenReturn(Optional.of(searches.getFirst()));
        searches.getFirst().setTimeSlots(timeSlots);

        Optional<Search> optSearch = searchServiceImpl.findById(searches.getFirst().getId());

        verify(searchRepository, times(1)).findById(searches.getFirst().getId());
        assertEquals(optSearch, Optional.of(searches.getFirst()));
    }

    @Test
    void findById_wrongId_optionalEmpty() {
        when(searchRepository.findById(searches.getLast().getId())).thenReturn(Optional.empty());
        Optional<Search> optSearch = searchServiceImpl.findById(searches.getLast().getId());

        verify(searchRepository, times(1)).findById(searches.getLast().getId());
        assertEquals(optSearch, Optional.empty());
    }

    @Test
    void findByClimberProfileId_profileId_optionalSetSearch(){
        when(searchRepository.findByClimberProfileId(searches.get(3).getClimberProfileId())).thenReturn(Optional.of(Set.of(searches.get(3))));
        searches.get(3).setTimeSlots(timeSlots);

        Optional<Set<Search>> optSearches = searchServiceImpl.findByClimberProfileId(4L);

        verify(searchRepository, times(1)).findByClimberProfileId(searches.get(3).getClimberProfileId());
        assertEquals(optSearches, Optional.of(Set.of(searches.get(3))));
    }

    @Test
    void findByClimberProfileId_wrongProfileId_empty(){
        when(searchRepository.findByClimberProfileId(searches.get(2).getClimberProfileId())).thenReturn(Optional.empty());
        Optional<Set<Search>> optSearches = searchServiceImpl.findByClimberProfileId(2L);

        verify(searchRepository, times(1)).findByClimberProfileId(searches.get(2).getClimberProfileId());
        assertEquals(optSearches, Optional.empty());
    }

    @Test
    void createSearch_insert(){
        Set<ClimbLevel> climbLevelsToCreate = Stream.of(
                ClimbLevel.builder().id(2L).codeFr(null).build(),
                ClimbLevel.builder().id(7L).codeFr(null).build()

        ).collect(Collectors.toSet());

        Search searchToCreate = Search.builder()
                        .id(1L)
                        .climberProfileId(1L)
                        .title("search1Profile1")
                        .haveRope(true)
                        .haveBelayDevice(true)
                        .haveQuickdraw(true)
                        .haveCarToShare(true)
                        .placeId(1L)
                        .preferedGenderId(1L)
                        .climbLevels(climbLevelsToCreate)
                        .isActive(true)
                        .build();
        searchToCreate.setTimeSlots(timeSlots);

        when(climbLevelService.findCimbLevelsByIds(climbLevelsToCreate)).thenReturn(climbLevels);
        when(searchRepository.save(ArgumentMatchers.any())).thenReturn(searches.getFirst());

        Search search = searchServiceImpl.createSearch(searchToCreate);

        verify(searchRepository, times(1)).save(ArgumentMatchers.any());
        assertEquals(search, searches.getFirst());
        assertEquals(search.getTimeSlots(), searches.getFirst().getTimeSlots());
        assertEquals(search.getClimbLevels(), searches.getFirst().getClimbLevels());
        assertEquals(search.getClimberProfileId(), searches.getFirst().getClimberProfileId());
    }

    @Test
    void updateSearch_update() {
        Search searchToUpdate = Search.builder()
                .id(1L)
                .climberProfileId(1L)
                .title("search1modified")
                .haveRope(true)
                .haveBelayDevice(true)
                .haveQuickdraw(true)
                .haveCarToShare(true)
                .placeId(1L)
                .preferedGenderId(1L)
                .climbLevels(climbLevels)
                .isActive(true)
                .build();
        searchToUpdate.setTimeSlots(timeSlot2);

        when(searchRepository.save(ArgumentMatchers.any())).thenReturn(searches.getFirst());
        when(searchRepository.save(searchToUpdate)).thenReturn(searchToUpdate);

        searchServiceImpl.updateSearch(searches.getFirst());
        Search searchUpdated = searchServiceImpl.updateSearch(searchToUpdate);

        verify(searchRepository, times(2)).save(ArgumentMatchers.any());
        assertEquals(searchUpdated, searchToUpdate);
        assertEquals(searchUpdated.getTitle(), searchToUpdate.getTitle());
        assertEquals(searchUpdated.getClimbLevels(), searches.getFirst().getClimbLevels());
        assertEquals(searchUpdated.getTimeSlots(), timeSlot2);
    }

    @Test
    void deleteEmployeeById_ok() {
        searchServiceImpl.deleteById(searches.get(2).getId());
        verify(searchRepository, times(1)).deleteById(searches.get(2).getId());
    }
}
