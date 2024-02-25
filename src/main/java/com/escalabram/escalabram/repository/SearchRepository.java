package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.Search;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface SearchRepository extends JpaRepository<Search, Long> {

    // FIXME Vérifier si utile et supprimer si besoin
    // List<Search> findAll();

    // FIXME peut on l'enlever aussi?
    Optional<Set<Search>> findByProfileId(Long profileId);

//    List<Search> findByHaveRope(boolean haveRope);
//
//    List<Search> findByMinClimbingLevelIdGreaterThanEqual(Long minClimbingLevelId);

}
