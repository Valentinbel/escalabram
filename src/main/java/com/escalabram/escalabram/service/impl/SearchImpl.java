package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimbLevel;
import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.repository.ClimbLevelRepository;
import com.escalabram.escalabram.repository.SearchRepository;
import com.escalabram.escalabram.service.SearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class SearchImpl implements SearchService {

    private final SearchRepository searchRepository;
    private final ClimbLevelRepository climbLevelRepository;

    public SearchImpl(SearchRepository searchRepository, ClimbLevelRepository climbLevelRepository) {
        this.searchRepository = searchRepository;
        this.climbLevelRepository = climbLevelRepository;
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
        // Le faire en iterator de Stream
        // Mettre dans l'ordre?
        Set<ClimbLevel> climbLevelList = newSearch.getClimbLevels();//.stream().findFirst();
        Set<ClimbLevel> newClimbLevels = new HashSet<>();
        climbLevelList.forEach(foreachClimb -> System.out.println("FOREAAAACH : " + foreachClimb));
        if (!climbLevelList.isEmpty()) {
            Long climbLevelId = climbLevelList.stream().findFirst().get().getId();
            Optional<ClimbLevel> climbLevel = climbLevelRepository.findById(climbLevelId);
            if(climbLevel.isPresent())
                newClimbLevels.add(climbLevel.get());

            newSearch.setClimbLevels(newClimbLevels);
        }
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
