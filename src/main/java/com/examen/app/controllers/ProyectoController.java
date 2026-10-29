package com.examen.app.controllers;

import com.examen.app.models.Proyecto;
import com.examen.app.models.Usuario;
import com.examen.app.repositories.ProyectoRepository;
import com.examen.app.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<?> crearProyecto(@RequestBody Proyecto proyecto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioLogueado = usuarioRepository.findByUsername(username).orElseThrow();

        long totalProyectos = proyectoRepository.countByUsuarioId(usuarioLogueado.getId());
        if (totalProyectos >= 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Alcanzó el límite de 3 proyectos.");
        }

        proyecto.setUsuario(usuarioLogueado);
        return ResponseEntity.status(HttpStatus.CREATED).body(proyectoRepository.save(proyecto));
    }

    @GetMapping
    public ResponseEntity<?> listarMisProyectos() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioLogueado = usuarioRepository.findByUsername(username).orElseThrow();
        return ResponseEntity.ok(proyectoRepository.findByUsuarioId(usuarioLogueado.getId()));
    }
}
