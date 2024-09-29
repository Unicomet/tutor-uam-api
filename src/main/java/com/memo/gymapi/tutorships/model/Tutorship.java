package com.memo.gymapi.tutorships.model;

import com.memo.gymapi.registration.model.Tutoree;
import com.memo.gymapi.tutors.model.Tutor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "asesoria", indexes = {
        @Index(name = "fk_asesoria_asesor2_idx", columnList = "asesor_id"),
        @Index(name = "fk_asesoria_asesorado2_idx", columnList = "asesorado_id")
})
public class Tutorship {
    @Id
    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asesor_id", nullable = false)
    private Tutor tutor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asesorado_id", nullable = false)
    private Tutoree tutoree;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime dateTime;
}