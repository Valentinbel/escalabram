package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.repository.SearchRepository;
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

    public SearchImpl(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
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
    public Search save(Search search) {
        return searchRepository.save(search);
    }

    @Override
    public void deleteById(Long id) {
        searchRepository.deleteById(id);
    }

//    @Override
//    public List<Search> findByHaveRope(boolean haveRope) {
//        return searchRepository.findByHaveRope(haveRope);
//    }
//
//    @Override
//    public List<Search> findByMinClimbingLevelIdGreaterThanEqual(Long minClimbingLevelId) {
//        return searchRepository.findByMinClimbingLevelIdGreaterThanEqual(minClimbingLevelId);
//    }
}
