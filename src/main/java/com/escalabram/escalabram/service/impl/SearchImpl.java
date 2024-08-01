package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimbLevel;
import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.repository.SearchRepository;
import com.escalabram.escalabram.service.ClimbLevelService;
import com.escalabram.escalabram.service.SearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class SearchImpl implements SearchService {

    private final SearchRepository searchRepository;
    private final ClimbLevelService climbLevelService;

    public SearchImpl(SearchRepository searchRepository, ClimbLevelService climbLevelService) {
        this.searchRepository = searchRepository;
        this.climbLevelService = climbLevelService;
    }

    @Override
    public List<Search> findAll() {
        return searchRepository.findAll();
    }

    @Override
    public Optional<Set<Search>> findByProfileId(Long profileId) {
        return searchRepository.findByProfileId(profileId);
    }

    @Override
    public Search createSearch(Search newSearch) {
        Set<ClimbLevel> newClimbLevels = climbLevelService.retrieveCimbLevelsFromIds(newSearch.getClimbLevels());
        newSearch.setClimbLevels(newClimbLevels);
        return newSearch;
    }

    @Override
    public Search save(Search search) {
        return searchRepository.save(search);
    }

    @Override
    public void deleteById(Long id) {
        searchRepository.deleteById(id);
    }
}
