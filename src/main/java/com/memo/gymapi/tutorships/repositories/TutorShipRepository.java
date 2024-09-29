package com.memo.gymapi.tutorships.repositories;

import com.memo.gymapi.tutorships.model.Tutorship;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TutorShipRepository extends CrudRepository<Tutorship, Integer> {

    Boolean existsByDateTime(LocalDateTime dateTime);

    List<Tutorship> findAllByTutoreeId(Integer tutoreeId);
}
