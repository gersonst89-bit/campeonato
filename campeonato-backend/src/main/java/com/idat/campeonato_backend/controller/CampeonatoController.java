package com.idat.campeonato_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idat.campeonato_backend.dto.CampeonatoRequestDTO;
import com.idat.campeonato_backend.dto.CampeonatoResponseDTO;
import com.idat.campeonato_backend.service.CampeonatoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/campeonatos")
@RequiredArgsConstructor
public class CampeonatoController {

    private final CampeonatoService campeonatoService;

    @GetMapping
    public ResponseEntity<List<CampeonatoResponseDTO>> getAll() {
        return ResponseEntity.ok(campeonatoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampeonatoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(campeonatoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CampeonatoResponseDTO> create(@Valid @RequestBody CampeonatoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(campeonatoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampeonatoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CampeonatoRequestDTO dto) {
        return ResponseEntity.ok(campeonatoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        campeonatoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}