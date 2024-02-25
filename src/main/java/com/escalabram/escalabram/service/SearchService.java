package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.Search;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SearchService {

    List<Search> findAll();

    Optional<Set<Search>> findByProfileId(Long profileId);

    Search save(Search search);

    void deleteById(Long id);

//    List<Search> findByHaveRope(boolean haveRope);
//
//    List<Search> findByMinClimbingLevelIdGreaterThanEqual(Long minClimbingLevelId);
}