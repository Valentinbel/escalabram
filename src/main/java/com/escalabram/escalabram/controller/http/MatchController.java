package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.model.Match;
import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.service.MatchService;
import com.escalabram.escalabram.service.SearchService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class MatchController {
    private final Logger log = LoggerFactory.getLogger(MatchController.class);
    private final MatchService matchService;
    private final SearchService searchService;

    public MatchController(MatchService matchService, SearchService searchService) {
        this.matchService = matchService;
        this.searchService = searchService;
    }

    @GetMapping("/matches/search/{searchId}")
    public ResponseEntity<List<Match>> getMatchesBySearchId(@PathVariable Long searchId) {
        log.debug("REST request to get list of matches, or create them if necessary, from searchId: {}", searchId);
        try {
            List<Match> matchList = new ArrayList<>();
            Optional<Search> optionalSearch = searchService.findById(searchId);
            if(optionalSearch.isPresent()) {
                Search search = optionalSearch.get();
                matchList = matchService.createMatchesIfFit(search);
            }
            return ResponseEntity.ok(matchList);

        } catch (Exception e) {
            log.error("An error was encountered while retrieving data from Matches",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
