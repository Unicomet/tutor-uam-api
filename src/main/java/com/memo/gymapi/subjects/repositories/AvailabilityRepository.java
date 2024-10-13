package com.memo.gymapi.subjects.repositories;

import com.memo.gymapi.subjects.dto.Day;
import com.memo.gymapi.subjects.model.Availability;
import com.memo.gymapi.tutors.model.Tutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface AvailabilityRepository extends CrudRepository<Availability, Integer> {

    @Query("SELECT s FROM Availability s WHERE :time >= s.startTime AND :time <= s.endTime AND s.day = :day AND s.tutor = :tutor")
    List<Availability> findSchedulesWithinTime(@Param("time") LocalTime time, @Param("day") Day day, @Param("tutor") Tutor tutor);


    List<Availability> findAllByTutorId(Integer id);
}
