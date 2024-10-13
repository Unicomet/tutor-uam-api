package com.memo.gymapi.tutorships.repositories;

import com.memo.gymapi.tutorships.model.EvaluationTutoree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationTutoreeRepository extends JpaRepository<EvaluationTutoree, Integer> {

    Boolean existsEvaluationTutoreeByTutorshipId(Integer tutorshipId);

}
