package com.idat.campeonato_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.idat.campeonato_backend.dto.CampeonatoRequestDTO;
import com.idat.campeonato_backend.dto.CampeonatoResponseDTO;
import com.idat.campeonato_backend.entity.Campeonato;
import com.idat.campeonato_backend.exception.ResourceNotFoundException;
import com.idat.campeonato_backend.repository.CampeonatoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampeonatoService {

    private final CampeonatoRepository campeonatoRepository;

    public List<CampeonatoResponseDTO> findAll() {
        return campeonatoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public CampeonatoResponseDTO findById(Long id) {
        Campeonato campeonato = campeonatoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campeonato no encontrado: " + id));
        return toResponseDTO(campeonato);
    }

    public CampeonatoResponseDTO save(CampeonatoRequestDTO dto) {
        return toResponseDTO(campeonatoRepository.save(toEntity(dto)));
    }

    public CampeonatoResponseDTO update(Long id, CampeonatoRequestDTO dto) {
        Campeonato existente = campeonatoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campeonato no encontrado: " + id));
        existente.setNombre(dto.getNombre());
        existente.setFechaInicio(dto.getFechaInicio());
        existente.setCantidadEquipos(dto.getCantidadEquipos());
        return toResponseDTO(campeonatoRepository.save(existente));
    }

    public void delete(Long id) {
        if (!campeonatoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Campeonato no encontrado: " + id);
        }
        campeonatoRepository.deleteById(id);
    }


    private CampeonatoResponseDTO toResponseDTO(Campeonato c) {
        return new CampeonatoResponseDTO(
                c.getId(),
                c.getNombre(),
                c.getFechaInicio(),
                c.getCantidadEquipos());
    }

    private Campeonato toEntity(CampeonatoRequestDTO dto) {
        Campeonato c = new Campeonato();
        c.setNombre(dto.getNombre());
        c.setFechaInicio(dto.getFechaInicio());
        c.setCantidadEquipos(dto.getCantidadEquipos());
        return c;
    }
}