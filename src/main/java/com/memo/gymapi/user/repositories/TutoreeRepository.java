package com.memo.gymapi.user.repositories;

import com.memo.gymapi.user.model.TutoreeEntity;
import com.memo.gymapi.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TutoreeRepository extends JpaRepository<TutoreeEntity, Integer> {
    //     Integer findByUserId(Integer userId);
//    @Query("SELECT t.id FROM TutoreeEntity t WHERE t.userEntity = :userId")
//    Optional<Integer> findIdByUserId(@Param("userId") Integer userId);

    @Query("SELECT t.id FROM TutoreeEntity t WHERE t.userEntity.id = :user_id")
    Optional<Integer> findIdByUserId(Integer user_id);


    TutoreeEntity findByUserEntity(UserEntity userEntity);
}
