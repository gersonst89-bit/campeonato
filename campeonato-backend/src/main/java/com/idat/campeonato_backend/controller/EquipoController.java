package com.idat.campeonato_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idat.campeonato_backend.dto.EquipoRequestDTO;
import com.idat.campeonato_backend.dto.EquipoResponseDTO;
import com.idat.campeonato_backend.service.EquipoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/equipos")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class EquipoController {

    private final EquipoService equipoService;

    @GetMapping
    public ResponseEntity<List<EquipoResponseDTO>> getAll() {
        return ResponseEntity.ok(equipoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipoResponseDTO> getById(@PathVariable Long id) { 
        return ResponseEntity.ok(equipoService.findById(id));
    }

    @GetMapping("/campeonato/{campeonatoId}")
    public ResponseEntity<List<EquipoResponseDTO>> getByCampeonato( 
            @PathVariable Long campeonatoId) {
        return ResponseEntity.ok(equipoService.findByCampeonato(campeonatoId));
    }

    @PostMapping
    public ResponseEntity<EquipoResponseDTO> create(
            @Valid @RequestBody EquipoRequestDTO dto) { 
        return ResponseEntity.status(HttpStatus.CREATED).body(equipoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody EquipoRequestDTO dto) { 
        return ResponseEntity.ok(equipoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        equipoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}