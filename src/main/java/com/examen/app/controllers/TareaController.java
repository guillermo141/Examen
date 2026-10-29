package com.examen.app.controllers;

import com.examen.app.models.Proyecto;
import com.examen.app.models.Tarea;
import com.examen.app.repositories.ProyectoRepository;
import com.examen.app.repositories.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    @PostMapping
    public ResponseEntity<?> crearTarea(@RequestBody Tarea tarea) {
        if (tarea.getProyecto() == null || tarea.getProyecto().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Falta ID del proyecto.");
        }

        Optional<Proyecto> proyecto = proyectoRepository.findById(tarea.getProyecto().getId());
        if (proyecto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Proyecto no encontrado.");
        }

        tarea.setProyecto(proyecto.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(tareaRepository.save(tarea));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody Tarea tareaActualizada) {
        Optional<Tarea> tareaExistente = tareaRepository.findById(id);
        if (tareaExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Tarea no encontrada.");
        }

        Tarea tarea = tareaExistente.get();
        tarea.setEstado(tareaActualizada.getEstado());
        return ResponseEntity.ok(tareaRepository.save(tarea));
    }
}