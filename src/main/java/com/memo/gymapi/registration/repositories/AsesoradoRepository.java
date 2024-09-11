package com.memo.gymapi.registration.repositories;

import com.memo.gymapi.registration.model.Asesorado;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AsesoradoRepository extends JpaRepository<Asesorado, Integer> {

}
