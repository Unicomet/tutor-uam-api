package com.memo.gymapi.tutorships.repositories;

import com.memo.gymapi.tutorships.model.EvaluationTutoreeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationTutoreeRepository extends JpaRepository<EvaluationTutoreeEntity, Integer> {

    Boolean existsEvaluationTutoreeEntityByTutorshipEntityId(Integer tutorshipId);

}
