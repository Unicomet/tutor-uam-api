package com.memo.gymapi.tutorships.model;

import com.memo.gymapi.registration.model.Tutoree;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "evaluacion_asesorado", indexes = {
        @Index(name = "fk_evaluacion_asesor_asesorado1_idx", columnList = "asesorado_id"),
        @Index(name = "fk_evaluacion_asesor_asesor1_idx", columnList = "asesor_id")
})
public class EvaluationTutoree {
    @Id
    @Column(name = "_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "calificacion", nullable = false, precision = 2)
    private Integer calificacion;

    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asesorado_id", nullable = false)
    private Tutoree tutoree;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asesoria_id", nullable = false)
    private Tutorship tutorship;
}