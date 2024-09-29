package com.memo.gymapi.registration.repositories;

import com.memo.gymapi.registration.model.Tutoree;
import com.memo.gymapi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TutoreeRepository extends JpaRepository<Tutoree, Integer> {
    //     Integer findByUserId(Integer userId);
//    @Query("SELECT t.id FROM Tutoree t WHERE t.user = :userId")
//    Optional<Integer> findIdByUserId(@Param("userId") Integer userId);

    @Query("SELECT t.id FROM Tutoree t WHERE t.user.id = :user_id")
    Optional<Integer> findIdByUserId(Integer user_id);


    Tutoree findByUser(User user);
}
