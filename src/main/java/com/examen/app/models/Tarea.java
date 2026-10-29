package com.examen.app.models;

import jakarta.persistence.*;
import lombok.Data;
import com.examen.app.models.enums.EstadoTarea;

@Data
@Entity
@Table(name = "tareas")
public class Tarea {

    @Enumerated(EnumType.STRING)
    private EstadoTarea estado;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;
}
