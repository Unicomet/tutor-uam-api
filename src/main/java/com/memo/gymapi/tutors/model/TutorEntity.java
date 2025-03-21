package com.memo.gymapi.tutors.model;

import com.memo.gymapi.user.model.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "asesor", indexes = {
        @Index(name = "fk_asesor_usuario1_idx", columnList = "usuario_id"),
        @Index(name = "fk_asesor_table11_idx", columnList = "disponibilidad")
})
public class TutorEntity {
    @Id
    @Column(name = "_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(name = "`descripción`", nullable = false)
    private String description;

    @Column(name = "grado_estudios", nullable = false, length = 100)
    private String degree;

    @Column(name = "campo_estudios", nullable = false, length = 100)
    private String studyField;

    @Column(name = "calificacion", precision = 2)
    private Float score;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disponibilidad")
    private Availability availability;

    @Column(name = "lugar_asesorias", nullable = false, length = 50)
    private String tutorshipPlace;
}