package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.exception.BadRequestAlertException;
import com.escalabram.escalabram.model.ClimbLevel;
import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.model.TimeSlot;
import com.escalabram.escalabram.repository.SearchRepository;
import com.escalabram.escalabram.service.ClimbLevelService;
import com.escalabram.escalabram.service.ProfileService;
import com.escalabram.escalabram.service.SearchService;
import com.escalabram.escalabram.dto.SearchDTO;
import com.escalabram.escalabram.dto.SearchListDTO;
import com.escalabram.escalabram.service.mapper.SearchMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private static final Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);
    private final SearchRepository searchRepository;
    private final ClimbLevelService climbLevelService;
    private final ProfileService profileService;
    private final SearchMapper searchMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SearchListDTO> findAll() {
         List<Search> searches = searchRepository.findAll();
        return searchMapper.toSearchListDTOs(searches);
    }

    @Override
    public Optional<Search> findById(Long searchId) {
        return searchRepository.findById(searchId);
    }

    @Override
    public Optional<Set<Search>> findByProfileId(Long profileId) {
        return searchRepository.findByProfileId(profileId);
    }

    @Override
    public SearchDTO saveSearch(SearchDTO searchDTO) {
        if(!profileService.existsById(searchDTO.getProfileId()))
            throw new BadRequestAlertException("There is no Profile matching with this search");

        Set<ClimbLevel> newClimbLevels = climbLevelService.findCimbLevelsByIds(searchDTO.getClimbLevels());
        searchDTO.setClimbLevels(newClimbLevels);

        //TODO Gérer la date avec UTC (actuellement décallé d'une heure => Instant?
        // https://claude.ai/chat/14d87630-dcaf-49a3-9d8a-048c510f4859

        //https://claude.ai/chat/22fb5e41-382f-4fc4-9e04-2fb150c0ac4d
        Search search = searchMapper.toSearch(searchDTO);
        log.info("searchToSave: {}", search);

        // Reconstruction manuelle des TimeSlots avec la référence search
        Set<TimeSlot> slots = searchDTO.getTimeSlots().stream()
                .map(dateTime -> TimeSlot.builder()
                        .beginTime(dateTime)
                        .search(search) // référence bidirectionnelle
                        .build())
                .collect(Collectors.toSet());

        search.setTimeSlots(slots);

        Search savedSearch = searchRepository.save(search);
        return searchMapper.toSearchDTO(savedSearch);
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
