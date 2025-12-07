package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    @Modifying
    @Query("update User u set u.userName = :userName, u.updatedAt = :updatedAt where u.id = :id")
    int updateUserNameById(@Param("id") Long userId, @Param("userName") String userName, @Param("updatedAt") LocalDateTime updatedAt);
}
