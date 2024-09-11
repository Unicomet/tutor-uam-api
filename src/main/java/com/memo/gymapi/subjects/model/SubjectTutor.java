package com.memo.gymapi.subjects.model;

import com.memo.gymapi.tutors.model.Tutor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "materia_asesor", indexes = {
        @Index(name = "fk_materia_asesor_asesor1_idx", columnList = "asesor_id"),
        @Index(name = "fk_materia_asesor_materia1_idx", columnList = "materia_clave")
})
public class SubjectTutor {
    @Id
    @Column(name = "_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asesor_id", nullable = false)
    private Tutor tutor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "materia_clave", nullable = false)
    private Subject subjectClave;

}