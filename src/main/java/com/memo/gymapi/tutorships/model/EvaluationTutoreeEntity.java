package com.memo.gymapi.tutorships.model;

import com.memo.gymapi.user.model.TutoreeEntity;
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
})
public class EvaluationTutoreeEntity {
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
    private TutoreeEntity tutoree;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asesoria_id", nullable = false)
    private TutorshipEntity tutorshipEntity;
}