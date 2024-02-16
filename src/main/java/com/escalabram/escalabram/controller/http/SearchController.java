package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.repository.SearchRepository;
import com.escalabram.escalabram.service.SearchService;
import com.escalabram.escalabram.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class SearchController {
    private final Logger log = LoggerFactory.getLogger(SearchController.class);
    private final SearchService searchService;


    public SearchController(SearchService searchService){
        this.searchService = searchService;
    }

    @Autowired
    SearchRepository searchRepository;

    @GetMapping("/searches")
    public ResponseEntity<List<Search>> getAllSearchs(){
        log.debug("REST request to get list of All Searches");
        try{
            List<Search> searches = searchService.findAll();
            return new ResponseEntity<>(searches, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searches/{profileId}")
    public ResponseEntity<Set<Search>> getSearchByProfileId(@PathVariable("profileId") long profileId ){
        log.debug("REST request to get list of Searches by profileId : {}", profileId);
        //Optional<List<Search>> searches= searchService.findByProfileId(profileId);
        return ResponseUtil.wrapOrNotFound(searchService.findByProfileId(profileId));
    }

//    @GetMapping("/searches/{haveRope}")
//    public ResponseEntity<Set<Search>> getSearchByHaveRope(@PathVariable("haveRope") boolean haveRope){
//        log.debug("REST request to get list of Searches by haveRope : {}", haveRope);
//        //try{
//            Set<Search> searches = searchService.findByHaveRope(haveRope);
//            return new ResponseEntity<Set<Search>>(searches, HttpStatus.OK);
////        }catch (Exception e){
////            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
////        }
//    }

//    @GetMapping("/searches/{minClimbingLevelId}")
//    public ResponseEntity<List<Search>> getSearchByHaveRope(@PathVariable("haveRope") long minClimbingLevelId){
//        log.debug("REST request to get list of Searches by minClimbingLevelId : {}", minClimbingLevelId);
//        try{
//            List<Search> searches = searchService.findByMinClimbingLevelIdGreaterThanEqual(minClimbingLevelId);
//            return new ResponseEntity<>(searches, HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
