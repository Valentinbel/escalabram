package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.exception.BadRequestAlertException;
import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.service.ClimberProfileService;
import com.escalabram.escalabram.service.SearchService;
import com.escalabram.escalabram.utils.ResponseUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;


//@CrossOrigin(origins = {"http://localhost:4200"}, maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api")
public class SearchController {
    private static final Logger log = LoggerFactory.getLogger(SearchController.class);
    private final SearchService searchService;
    private final ClimberProfileService climberProfileService;

    public SearchController(SearchService searchService, ClimberProfileService climberProfileService){
        this.searchService = searchService;
        this.climberProfileService = climberProfileService;
    }

    @GetMapping("/searches")
    public ResponseEntity<List<Search>> getAllSearches(){
        log.debug("REST request to get list of All Searches");
        try{
            List<Search> searches = searchService.findAll();
            return ResponseEntity.ok(searches);
        } catch (Exception e){
            log.error("An error was encountered while retrieving data from Searches",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/searches/climber-profiles/{id}")
    public ResponseEntity<Set<Search>> getSearchByClimberProfileId(@PathVariable("id") Long id ){
        log.debug("REST request to get list of Search by climberProfileId: {}", id);
        try {
            Optional<Set<Search>> searches= searchService.findByClimberProfileId(id);
            log.debug("SEARCHES: {}", searches);
            return ResponseUtil.wrapOrNotFound(searches);
        } catch (Exception e) {
            log.error("An error was encountered while retrieving data in getSearchByClimberProfileId",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/searches")
    public ResponseEntity<Search> createSearch(@Valid @RequestBody Search search) {
        log.debug("REST request to save Search : {}", search);
        try {
            if (search.getId() != null)
                throw new BadRequestAlertException("A new search cannot already have an ID");

            if(!climberProfileService.existsById(search.getClimberProfileId()))
                throw new BadRequestAlertException("There is no ClimberProfile matching with this search");

            // TODO Remplacer SEARCH par un DTO
            Search createdSearch = searchService.createSearch(search);

            return new ResponseEntity<>(createdSearch, HttpStatus.CREATED);
            //return ResponseEntity.status(HttpStatus.OK).build(createdSearch);
            //return ResponseEntity.created(new URI("/api/personne-autorises/" + result.getId())).body(result);
        } catch (Exception e) {
            log.error("An error was encountered while retrieving data",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/searches")
    public ResponseEntity<Search> updateSeach(@Valid @RequestBody Search search) {
        log.debug("REST request to update Search : {}", search);
        if (search.getId() == null)
            throw new BadRequestAlertException("You cannot update a Search that have no Id");
        Search updatedSearch = searchService.updateSearch(search);
        return ResponseEntity.ok().body(updatedSearch);
    }

    @DeleteMapping("/searches/{id}")
    public ResponseEntity<HttpStatus> deleteSearch(@PathVariable Long id) {
        log.debug("REST request to delete Search : {}", id);
        try {
            searchService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error during Searh deletion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

// TODO import fr.gouv.justice.tig.web.rest.errors.BadRequestAlertException; (Tig-Lib-Shared)
// Creer ma badRequestAlert implémenter commme URI Alert
// Vérifier dans les autres fonctions ouvertes de TIG si on a tout ok.
// Implémenter le test.
//
