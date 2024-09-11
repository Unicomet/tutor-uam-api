package com.memo.gymapi;

import com.memo.gymapi.tutors.model.Tutor;
import com.memo.gymapi.registration.model.Asesorado;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "asesoria", indexes = {
        @Index(name = "fk_asesoria_asesor2_idx", columnList = "asesor__id"),
        @Index(name = "fk_asesoria_asesorado2_idx", columnList = "asesorado__id")
})
public class Asesoria {
    @Id
    @Column(name = "_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asesor__id", nullable = false)
    private Tutor tutor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asesorado__id", nullable = false)
    private Asesorado asesorado;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fin")
    private LocalTime horaFin;

    @Column(name = "fecha")
    private LocalDate fecha;

}