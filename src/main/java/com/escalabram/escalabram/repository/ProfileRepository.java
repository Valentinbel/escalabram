package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    //List<Profile> findAll();
}
