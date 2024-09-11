package com.memo.gymapi.tutors.model;

import com.memo.gymapi.subjects.model.Availability;
import com.memo.gymapi.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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
public class Tutor {
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
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disponibilidad")
    private Availability availability;

}