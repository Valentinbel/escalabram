package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimbLevel;
import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.model.TimeSlot;
import com.escalabram.escalabram.repository.SearchRepository;
import com.escalabram.escalabram.service.ClimbLevelService;
import com.escalabram.escalabram.service.SearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;
    private final ClimbLevelService climbLevelService;

    public SearchServiceImpl(SearchRepository searchRepository, ClimbLevelService climbLevelService) {
        this.searchRepository = searchRepository;
        this.climbLevelService = climbLevelService;
    }

    @Override
    public List<Search> findAll() {
        return searchRepository.findAll();
    }

    @Override
    public Optional<Search> findById(Long searchId) {
        Optional<Search> optSearch = searchRepository.findById(searchId);
        if(optSearch.isPresent() && optSearch.get().getClimbLevels().isEmpty()) {
            // TODO voir si c'est bien utile
            Set<ClimbLevel> climbLevels = climbLevelService.getClimbLevelsBySearchId(optSearch.get().getId());
            System.out.println("///////////////////////////////////////////////////////////////////////////////////");
            System.out.println("getClimbLevels().isEmpty() estait EMPTYYYYYYYYYY  dans SearchServiceImpl.findById");
            optSearch.get().setClimbLevels(climbLevels);
        }
        return optSearch;
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
            TimeSlot timeSlot = new TimeSlot(timeSlotIn.getId(), timeSlotIn.getBeginTime(), timeSlotIn.getEndTime());
            timeSlot.setSearch(search);
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
