package com.memo.gymapi.tutorships.repositories;

import com.memo.gymapi.tutorships.model.EvaluationTutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationTutorRepository extends JpaRepository<EvaluationTutorEntity, Integer> {

    Boolean existsEvaluationTutorByTutorshipEntityId(Integer tutorshipId);

}
