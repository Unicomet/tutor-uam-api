package com.memo.gymapi.tutorships.repositories;

import com.memo.gymapi.tutorships.model.EvaluationTutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationTutorRepository extends JpaRepository<EvaluationTutor, Integer> {

    Boolean existsEvaluationTutorByTutorshipId(Integer tutorshipId);

}
