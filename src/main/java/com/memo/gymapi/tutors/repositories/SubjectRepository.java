package com.memo.gymapi.tutors.repositories;

import com.memo.gymapi.tutors.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    Subject findByNombre(String subjectName);
}
