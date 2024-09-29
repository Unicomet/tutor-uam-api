package com.memo.gymapi.tutors.repositories;

import com.memo.gymapi.tutors.model.Tutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TutorPageRepository extends PagingAndSortingRepository<Tutor, Integer> {
    Page<Tutor> findAll(Pageable pageable);
}
