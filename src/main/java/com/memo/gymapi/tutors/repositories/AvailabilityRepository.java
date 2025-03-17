package com.memo.gymapi.tutors.repositories;

import com.memo.gymapi.tutors.model.Availability;
import com.memo.gymapi.tutors.model.Day;
import com.memo.gymapi.tutors.model.TutorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface AvailabilityRepository extends CrudRepository<Availability, Integer> {

    @Query("SELECT s FROM Availability s WHERE :time >= s.startTime AND :time <= s.endTime AND s.day = :day AND s.tutorEntity = :tutorEntity")
    List<Availability> findSchedulesWithinTime(@Param("time") LocalTime time, @Param("day") Day day, @Param("tutorEntity") TutorEntity tutorEntity);

    List<Availability> findAllByTutorEntityId(Integer id);
}
