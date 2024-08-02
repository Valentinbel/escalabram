package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.controller.errors.BadRequestAlertException;
import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.service.SearchService;
import com.escalabram.escalabram.utils.ResponseUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class SearchController {
    private final Logger log = LoggerFactory.getLogger(SearchController.class);
    private final SearchService searchService;


    public SearchController(SearchService searchService){
        this.searchService = searchService;
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

    @GetMapping("/searches/{climberProfileId}")
    public ResponseEntity<Set<Search>> getSearchByClimberProfileId(@PathVariable("climberProfileId") long climberProfileId ){
        log.debug("REST request to get list of Search by climberProfileId : {}", climberProfileId);
        //Optional<List<Search>> searches= searchService.findByProfileId(climberProfileId);
        return ResponseUtil.wrapOrNotFound(searchService.findByClimberProfileId(climberProfileId));
    }

    @PostMapping("/searches")
    public ResponseEntity<Search> createSearch(@Valid @RequestBody Search search) {
        log.debug("REST request to save Search : {}", search);
        try {
            if (search.getId() != null)
                throw new BadRequestAlertException("A new search cannot already have an ID");

            //Remplacer SEARCH par un DTO ///////////////////////////////////
            searchService.createSearch(search);

            return ResponseEntity.status(HttpStatus.OK).build();
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
        Search result = searchService.save(search);
        return ResponseEntity.ok().body(result);
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

// import fr.gouv.justice.tig.web.rest.errors.BadRequestAlertException; (Tig-Lib-Shared)
// Creer ma badRequestAlert implémenter commme URI Alert
// Vérifier dans les autres fonctions ouvertes de TIG si on a tout ok.
// Implémenter le test.
//
