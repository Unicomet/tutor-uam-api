package com.memo.gymapi.tutorships.repositories;

import com.memo.gymapi.tutorships.model.TutorshipEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TutorShipRepository extends CrudRepository<TutorshipEntity, Integer> {

    Boolean existsByDateTime(LocalDateTime dateTime);

    List<TutorshipEntity> findAllByTutoreeId(Integer tutoreeId);

    List<TutorshipEntity> findAllByTutorEntityId(Integer tutorId);
}
