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

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT language_id FROM climber_user WHERE id = :id ", nativeQuery = true)
    Long findLanguageIdById(Long id);

    @Modifying
    @Query("UPDATE User u SET u.userName = :userName, u.updatedAt = :updatedAt WHERE u.id = :id")
    int updateUserNameById(@Param("id") Long userId, @Param("userName") String userName, @Param("updatedAt") LocalDateTime updatedAt);

    @Modifying
    @Query("UPDATE User u SET u.language.id = :languageId, u.updatedAt = :updatedAt WHERE u.id = :id")
    int updateLanguageIdById(@Param("id") Long userId, @Param("languageId") Long languageId, @Param("updatedAt") LocalDateTime updatedAt);
}
