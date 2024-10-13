package com.memo.gymapi.registration.repositories;

import com.memo.gymapi.tutors.model.Tutor;
import com.memo.gymapi.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutor, Integer> {
    Tutor findByUser(User user);

    Tutor getAsesorByUser(User user);

    @Query("SELECT t.id FROM Tutor t WHERE t.user.id = :user_id")
    Optional<Integer> findIdByUserId(Integer user_id);

    Boolean existsByUserId(Integer userId);
}
