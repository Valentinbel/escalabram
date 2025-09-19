package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.ClimberUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClimberUserRepository extends JpaRepository<ClimberUser, Long> {

    Optional<ClimberUser> findByEmail(String email);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    @Modifying
    @Query("update ClimberUser u set u.userName = :userName where u.id = :id")
    int updateUserNameById(@Param("id") Long userId, @Param("userName") String userName);
}
