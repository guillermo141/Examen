package com.examen.app.repositories;

import com.examen.app.models.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    List<Proyecto> findByUsuarioId(Long usuarioId);
    long countByUsuarioId(Long usuarioId);
}