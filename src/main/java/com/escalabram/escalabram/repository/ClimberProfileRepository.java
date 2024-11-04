package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.ClimberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClimberProfileRepository extends JpaRepository<ClimberProfile, Long> {

}
