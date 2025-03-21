package com.memo.gymapi.tutors.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "materia")
public class Subject {
    @Id
    @Column(name = "_clave", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @ColumnDefault("0")
    @Column(name = "cantidad_asesorias")
    private Integer cantidadAsesorias;

    @ColumnDefault("0")
    @Column(name = "cantidad_asesores")
    private Integer cantidadAsesores;

}