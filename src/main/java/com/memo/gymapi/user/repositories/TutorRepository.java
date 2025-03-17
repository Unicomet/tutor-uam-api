package com.memo.gymapi.user.repositories;

import com.memo.gymapi.tutors.model.TutorEntity;
import com.memo.gymapi.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TutorRepository extends JpaRepository<TutorEntity, Integer> {
    TutorEntity findByUserEntity(UserEntity userEntity);

    TutorEntity getAsesorByUserEntity(UserEntity userEntity);

    @Query("SELECT t.id FROM TutorEntity t WHERE t.userEntity.id = :user_id")
    Optional<Integer> findIdByUserId(Integer user_id);

    Boolean existsByUserEntityId(Integer userId);
}
