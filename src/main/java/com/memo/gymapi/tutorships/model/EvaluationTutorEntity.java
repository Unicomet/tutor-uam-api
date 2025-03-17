package com.memo.gymapi.tutorships.model;

import com.memo.gymapi.tutors.model.TutorEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "evaluacion_asesor", indexes = {
        @Index(name = "fk_evaluacion_asesor_asesor1_idx", columnList = "asesor_id")
})
public class EvaluationTutorEntity {
    @Id
    @Column(name = "_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "calificacion", nullable = false, precision = 2)
    private Integer calificacion;

    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asesor_id", nullable = false)
    private TutorEntity tutorEntity;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asesoria_id", nullable = false)
    private TutorshipEntity tutorshipEntity;

}