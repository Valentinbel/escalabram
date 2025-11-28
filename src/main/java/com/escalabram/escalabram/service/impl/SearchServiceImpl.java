package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimbLevel;
import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.model.TimeSlot;
import com.escalabram.escalabram.repository.SearchRepository;
import com.escalabram.escalabram.service.ClimbLevelService;
import com.escalabram.escalabram.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;
    private final ClimbLevelService climbLevelService;

    @Override
    public List<Search> findAll() {
        return searchRepository.findAll();
    }

    @Override
    public Optional<Search> findById(Long searchId) {
        return searchRepository.findById(searchId);
    }

    @Override
    public Optional<Set<Search>> findByClimberProfileId(Long climberProfileId) {
        return searchRepository.findByClimberProfileId(climberProfileId);
    }

    @Override
    public Search createSearch(Search search) {
        Set<ClimbLevel> newClimbLevels = climbLevelService.findCimbLevelsByIds(search.getClimbLevels());
        search.setClimbLevels(newClimbLevels);

        Set<TimeSlot> timeslots = new HashSet<>();
        for (TimeSlot timeSlotIn : search.getTimeSlots()) {
            TimeSlot timeSlot = TimeSlot.builder()
                    .id(timeSlotIn.getId())
                    .beginTime(timeSlotIn.getBeginTime())
                    .endTime(timeSlotIn.getEndTime())
                    .search(search)
                    .build();
            timeslots.add(timeSlot);
        }
        search.setTimeSlots(timeslots);

        return searchRepository.save(search);
    }

    @Override
    public Search updateSearch(Search search) {
        return searchRepository.save(search);
    }

    @Override
    public void deleteById(Long id) {
        searchRepository.deleteById(id);
    }
}
