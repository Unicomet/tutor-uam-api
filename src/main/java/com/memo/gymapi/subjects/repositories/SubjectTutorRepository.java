package com.memo.gymapi.subjects.repositories;

import com.memo.gymapi.subjects.model.SubjectTutor;
import com.memo.gymapi.tutors.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectTutorRepository extends JpaRepository<SubjectTutor, Integer> {
    List<SubjectTutor> findAllByTutor(Tutor tutor);
}
