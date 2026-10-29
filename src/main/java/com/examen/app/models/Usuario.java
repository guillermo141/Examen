package com.examen.app.models;

import jakarta.persistence.*;
import lombok.Data;
import com.examen.app.models.enums.RolUsuario;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Enumerated(EnumType.STRING)
    private RolUsuario rol;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
}