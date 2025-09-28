package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.model.Match;
import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.service.MatchService;
import com.escalabram.escalabram.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = {"http://localhost:4200"}, maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MatchController {
    private static final Logger log = LoggerFactory.getLogger(MatchController.class);
    private final MatchService matchService;
    private final SearchService searchService;

    @GetMapping("/matches/search/{searchId}")
    public ResponseEntity<List<Match>> getMatchesBySearchId(@PathVariable Long searchId) {
        log.debug("REST request to get list of matches, or create them if necessary, from searchId: {}", searchId);
        try {
            List<Match> matches = new ArrayList<>();
            Optional<Search> optSearch = searchService.findById(searchId);
            if(optSearch.isPresent()) {
                Search search = optSearch.orElseThrow();
                matches = matchService.createMatchesIfFit(search);
            }
            return ResponseEntity.ok(matches);

        } catch (Exception e) {
            log.error("An error was encountered while retrieving data from Matches",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
