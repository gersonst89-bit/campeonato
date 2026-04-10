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

import com.idat.campeonato_backend.dto.PartidoRequestDTO;
import com.idat.campeonato_backend.dto.PartidoResponseDTO;
import com.idat.campeonato_backend.dto.TablaPosicionesResponseDTO;
import com.idat.campeonato_backend.service.PartidoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/partidos")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class PartidoController {

    private final PartidoService partidoService;

    @GetMapping
    public ResponseEntity<List<PartidoResponseDTO>> getAll() {
        return ResponseEntity.ok(partidoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(partidoService.findById(id));
    }

    @GetMapping("/campeonato/{id}")
    public ResponseEntity<List<PartidoResponseDTO>> getByCampeonato(@PathVariable Long id) {
        return ResponseEntity.ok(partidoService.findByCampeonato(id));
    }

    @GetMapping("/tabla/{campeonatoId}")
    public ResponseEntity<List<TablaPosicionesResponseDTO>> getTablaPosiciones( 
            @PathVariable Long campeonatoId) {
        return ResponseEntity.ok(partidoService.getTablaPosPorCampeonato(campeonatoId));
    }

    @PostMapping
    public ResponseEntity<PartidoResponseDTO> create(
            @Valid @RequestBody PartidoRequestDTO dto) { 
        return ResponseEntity.status(HttpStatus.CREATED).body(partidoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartidoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PartidoRequestDTO dto) { 
        return ResponseEntity.ok(partidoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        partidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}