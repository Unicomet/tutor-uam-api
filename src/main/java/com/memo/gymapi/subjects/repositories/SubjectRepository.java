package com.memo.gymapi.subjects.repositories;

import com.memo.gymapi.subjects.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long>{

    Subject findByNombre(String subjectName);
}
