package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimbLevel;
import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.model.TimeSlot;
import com.escalabram.escalabram.repository.SearchRepository;
import com.escalabram.escalabram.service.SearchService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
    public static Set<ClimbLevel> climbLevelSet;
    public static Set<TimeSlot> timeSlotSet;
    public static List<Search> searchList;

    @Autowired
    private SearchService searchService;

    @MockBean
    private SearchRepository searchRepository;


    @BeforeAll
    public static void init() {
        String beginTime1 = "2024-09-02 13:59:59.123456789";
        String endTime1 = "2024-09-02 18:59:59.123456789";

        String beginTime2 = "2024-09-03 19:00:00.123456789";
        String endTime2 = "2024-09-03 21:00:00.123456789";

        climbLevelSet = Stream.of(
                new ClimbLevel(2L ,"4+"),
                new ClimbLevel(7L ,"6A+")
                ).collect(Collectors.toSet());
        timeSlotSet = Stream.of(
                new TimeSlot(1L, Timestamp.valueOf(beginTime1), Timestamp.valueOf(endTime1)),
                new TimeSlot(1L, Timestamp.valueOf(beginTime2), Timestamp.valueOf(endTime2))
        ).collect(Collectors.toSet());
        searchList = Stream.of(
                new Search(1L,1L, "search1Profile1", true, true, true,
                        true, 1L,1L, climbLevelSet, true),
                new Search(2L,1L, "search2Profile1", true, true, true,
                        true, 2L,1L, climbLevelSet, true),
                new Search(3L,2L, "search1Profile2", true, true, true,
                        true, 2L,1L, climbLevelSet, true),
                new Search(4L,4L, "search1Profile3", true, true, true,
                        true, 2L,1L, climbLevelSet, true)
        ).toList();
    }

    @Test
    void testFindAll(){
        when(searchRepository.findAll()).thenReturn(searchList);
        searchList.get(3).setTimeSlots(timeSlotSet);

        List<Search> searches = searchService.findAll();

        assertFalse(searches.isEmpty());
        verify(searchRepository, times(1)).findAll();
        assertEquals(searches.size(), searchList.size());
        assertEquals(searches.get(3), searchList.get(3));
        assertEquals(searches.get(3).getTimeSlots(), searchList.get(3).getTimeSlots());
        assertEquals(searches.get(0).getClimbLevels(), searchList.get(0).getClimbLevels());
        assertEquals(searches.get(3).getId(), searchList.get(3).getId());
        assertEquals(searches.get(2).getPlaceId(), searchList.get(2).getPlaceId());
        assertEquals(searches.get(2).getTimeSlots(), searchList.get(2).getTimeSlots());
    }

    @Test
    void testFindById() {
        when(searchRepository.findById(searchList.getFirst().getId())).thenReturn(Optional.of(searchList.getFirst()));
        searchList.getFirst().setTimeSlots(timeSlotSet);

        Optional<Search> optSearch = searchService.findById(searchList.getFirst().getId());

        verify(searchRepository, times(1)).findById(searchList.getFirst().getId());
        assertEquals(optSearch, Optional.of(searchList.getFirst()));
    }

    @Test
    void testFindByClimberProfileId(){
        when(searchRepository.findByClimberProfileId(searchList.get(3).getClimberProfileId())).thenReturn(Optional.of(Set.of(searchList.get(3))));
        searchList.get(3).setTimeSlots(timeSlotSet);

        Optional<Set<Search>> optSetSearch = searchService.findByClimberProfileId(4L);

        verify(searchRepository, times(1)).findByClimberProfileId(searchList.get(3).getClimberProfileId());
        assertEquals(optSetSearch, Optional.of(Set.of(searchList.get(3))));
    }

//    @Test
//    void testCreateSearch(){
//
//
//    }
}
