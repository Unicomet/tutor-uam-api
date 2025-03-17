package com.memo.gymapi.tutors.repositories;

import com.memo.gymapi.tutors.model.SubjectTutor;
import com.memo.gymapi.tutors.model.TutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectTutorRepository extends JpaRepository<SubjectTutor, Integer> {
    List<SubjectTutor> findAllByTutorEntity(TutorEntity tutorEntity);
}
