package com.memo.gymapi.user.repositories;

import com.memo.gymapi.user.model.Faculty;
import com.memo.gymapi.user.model.Ocupation;
import com.memo.gymapi.user.model.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);

    Optional<Integer> findIdByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity SET faculty=:faculty, ocupation=:ocupation WHERE email=:email")
    void updateFacultyAndOcupationByEmail(@Param("email") String email, @Param("faculty") Faculty faculty, @Param("ocupation") Ocupation ocupation);
}


