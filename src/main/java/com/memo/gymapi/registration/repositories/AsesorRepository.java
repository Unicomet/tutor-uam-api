package com.memo.gymapi.registration.repositories;

import com.memo.gymapi.tutors.model.Tutor;
import com.memo.gymapi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsesorRepository extends JpaRepository<Tutor, Long> {
    Tutor findByUser(User user);
    Tutor getAsesorByUser(User user);
}
