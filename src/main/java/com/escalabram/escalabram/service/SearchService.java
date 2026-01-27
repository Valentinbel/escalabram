package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.service.dto.SearchDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SearchService {

    List<Search> findAll();

    Optional<Search> findById(Long searchId);

    Optional<Set<Search>> findByProfileId(Long profileId);

    SearchDTO saveSearch(SearchDTO searchDTO);

    Search updateSearch(Search search);

    void deleteById(Long id);
}
