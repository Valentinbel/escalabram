package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimbLevel;
import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.model.TimeSlot;
import com.escalabram.escalabram.repository.SearchRepository;
import com.escalabram.escalabram.service.ClimbLevelService;
import com.escalabram.escalabram.service.SearchService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

@SpringBootTest
class SearchServiceImplTest {
    public static Set<ClimbLevel> climbLevels;
    public static Set<TimeSlot> timeSlots;
    public static List<Search> searches;

    @Autowired
    private SearchService searchService;

    @MockBean
    private ClimbLevelService climbLevelService;

    @MockBean
    private SearchRepository searchRepository;


    @BeforeAll
    public static void init() {
        climbLevels = Stream.of(
                new ClimbLevel(2L ,"4+"),
                new ClimbLevel(7L ,"6A+")
        ).collect(Collectors.toSet());

        String beginTime1 = "2024-09-02 13:59:59.123456789";
        String endTime1 = "2024-09-02 18:59:59.123456789";

        String beginTime2 = "2024-09-03 19:00:00.123456789";
        String endTime2 = "2024-09-03 21:00:00.123456789";

        timeSlots = Stream.of(
                new TimeSlot(1L, Timestamp.valueOf(beginTime1), Timestamp.valueOf(endTime1)),
                new TimeSlot(1L, Timestamp.valueOf(beginTime2), Timestamp.valueOf(endTime2))
        ).collect(Collectors.toSet());

        searches = Stream.of(
                new Search(1L,1L, "search1Profile1", true, true, true,
                        true, 1L,1L, climbLevels, true),
                new Search(2L,1L, "search2Profile1", true, true, true,
                        true, 2L,1L, climbLevels, true),
                new Search(3L,2L, "search1Profile2", true, true, true,
                        true, 2L,1L, climbLevels, true),
                new Search(4L,4L, "search1Profile3", true, true, true,
                        true, 2L,1L, climbLevels, true)
        ).toList();
    }

    @Test
    void testFindAll(){
        when(searchRepository.findAll()).thenReturn(searches);
        searches.get(3).setTimeSlots(timeSlots);

        List<Search> findedSearches = searchService.findAll();

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
    void testFindById() {
        when(searchRepository.findById(searches.getFirst().getId())).thenReturn(Optional.of(searches.getFirst()));
        searches.getFirst().setTimeSlots(timeSlots);

        Optional<Search> optSearch = searchService.findById(searches.getFirst().getId());

        verify(searchRepository, times(1)).findById(searches.getFirst().getId());
        assertEquals(optSearch, Optional.of(searches.getFirst()));
    }

    @Test
    void testFindByClimberProfileId(){
        when(searchRepository.findByClimberProfileId(searches.get(3).getClimberProfileId())).thenReturn(Optional.of(Set.of(searches.get(3))));
        searches.get(3).setTimeSlots(timeSlots);

        Optional<Set<Search>> optSearches = searchService.findByClimberProfileId(4L);

        verify(searchRepository, times(1)).findByClimberProfileId(searches.get(3).getClimberProfileId());
        assertEquals(optSearches, Optional.of(Set.of(searches.get(3))));
    }

    @Test
    void testCreateSearch(){
        Set<ClimbLevel> climbLevelsToCreate = Stream.of(
                new ClimbLevel(2L , null),
                new ClimbLevel(7L ,null)
        ).collect(Collectors.toSet());

        Search searchToCreate = new Search(1L,1L, "search1Profile1", true, true, true,
                true, 1L,1L, climbLevelsToCreate, true);
        searchToCreate.setTimeSlots(timeSlots);

        when(climbLevelService.findCimbLevelsByIds(climbLevelsToCreate)).thenReturn(climbLevels);
        when(searchRepository.save(ArgumentMatchers.any())).thenReturn(searches.getFirst());

        Search search = searchService.createSearch(searchToCreate);

        verify(searchRepository, times(1)).save(ArgumentMatchers.any());
        assertEquals(search, searches.getFirst());
        assertEquals(search.getTimeSlots(), searches.getFirst().getTimeSlots());
        assertEquals(search.getClimbLevels(), searches.getFirst().getClimbLevels());
        assertEquals(search.getClimberProfileId(), searches.getFirst().getClimberProfileId());
    }

    @Test
    void testUpdateSearch() {

        Search searchToUpdate = new Search(1L,1L, "search1Profile1", true, true, true,
                true, 1L,1L, climbLevels, true);
        searchToUpdate.setTimeSlots(timeSlots);

        when(searchRepository.save(ArgumentMatchers.any())).thenReturn(searches.getFirst());

        Search search = searchService.updateSearch(searchToUpdate);

        verify(searchRepository, times(1)).save(ArgumentMatchers.any());
        assertEquals(search, searches.getFirst());
        assertEquals(search, searchToUpdate);
        assertEquals(search.getTitle(), searches.getFirst().getTitle());
        assertEquals(search.getClimbLevels(), searches.getFirst().getClimbLevels());
        assertEquals(search.getTimeSlots(), searches.getFirst().getTimeSlots());
    }

    @Test
    void deleteEmployeeById() {
        searchService.deleteById(searches.get(2).getId());
        verify(searchRepository, times(1)).deleteById(searches.get(2).getId());
    }
}
