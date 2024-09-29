package com.memo.gymapi.tutorships.model;

import com.memo.gymapi.registration.model.Tutoree;
import com.memo.gymapi.tutors.model.Tutor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "evaluacion_asesorado", indexes = {
        @Index(name = "fk_evaluacion_asesor_asesorado1_idx", columnList = "asesorado_id"),
        @Index(name = "fk_evaluacion_asesor_asesor1_idx", columnList = "asesor_id")
})
public class EvaluationTutoree {
    @Id
    @Column(name = "_id", nullable = false)
    private Integer id;

    @Column(name = "calificacion", nullable = false, precision = 2)
    private BigDecimal calificacion;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asesorado_id", nullable = false)
    private Tutoree tutoree;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asesor_id", nullable = false)
    private Tutor tutor;

}