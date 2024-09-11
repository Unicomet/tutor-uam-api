package com.memo.gymapi.tutors.repository;

import com.memo.gymapi.tutors.model.Tutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TutorRepository extends PagingAndSortingRepository<Tutor, Long> {
    Page<Tutor> findAll(Pageable pageable);
}
