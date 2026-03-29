package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.model.Match;
import com.escalabram.escalabram.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

//@CrossOrigin(origins = {"http://localhost:4200"}, maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MatchController {
    private static final Logger log = LoggerFactory.getLogger(MatchController.class);
    private final MatchService matchService;

    @GetMapping("/matches/search/{searchId}")
    public ResponseEntity<Set<Match>> createMatchesBySearchId(@PathVariable Long searchId) {
        log.info("REST request to create a list of matches if it's possible, from searchId: {}", searchId);
            return ResponseEntity.ok(matchService.createMatchesIfFit(searchId));
    }
}
