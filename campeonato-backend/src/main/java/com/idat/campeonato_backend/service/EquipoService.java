package com.idat.campeonato_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.campeonato_backend.dto.EquipoRequestDTO;
import com.idat.campeonato_backend.dto.EquipoResponseDTO;
import com.idat.campeonato_backend.entity.Campeonato;
import com.idat.campeonato_backend.entity.Equipo;
import com.idat.campeonato_backend.entity.TablaPosiciones;
import com.idat.campeonato_backend.exception.ResourceNotFoundException;
import com.idat.campeonato_backend.repository.CampeonatoRepository;
import com.idat.campeonato_backend.repository.EquipoRepository;
import com.idat.campeonato_backend.repository.TablaPosicionesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipoService {

    private final EquipoRepository equipoRepository;
    private final CampeonatoRepository campeonatoRepository;
    private final TablaPosicionesRepository tablaPosicionesRepository;

    public List<EquipoResponseDTO> findAll() {
        return equipoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList(); 
    }

    public EquipoResponseDTO findById(Long id) { 
        return toDTO(equipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado: " + id)));
    }

    public List<EquipoResponseDTO> findByCampeonato(Long campeonatoId) { 
        return equipoRepository.findByCampeonatoId(campeonatoId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public EquipoResponseDTO save(EquipoRequestDTO dto) {
        Campeonato campeonato = campeonatoRepository.findById(dto.getIdCampeonato())
                .orElseThrow(() -> new ResourceNotFoundException("Campeonato no encontrado: " + dto.getIdCampeonato()));

        long equiposActuales = equipoRepository.countByCampeonatoId(dto.getIdCampeonato());
        if (equiposActuales >= campeonato.getCantidadEquipos()) {
            throw new RuntimeException("El campeonato ya alcanzó el límite de "
                    + campeonato.getCantidadEquipos() + " equipos");
        }

        Equipo equipo = equipoRepository.save(toEntity(dto, campeonato));

        TablaPosiciones fila = new TablaPosiciones();
        fila.setEquipo(equipo);
        fila.setCampeonato(campeonato);
        fila.setPj(0);
        fila.setG(0);
        fila.setE(0);
        fila.setP(0);
        fila.setGf(0);
        fila.setGc(0);
        fila.setPts(0);
        tablaPosicionesRepository.save(fila);

        return toDTO(equipo);
    }

    public EquipoResponseDTO update(Long id, EquipoRequestDTO dto) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado: " + id));

        Campeonato campeonato = campeonatoRepository.findById(dto.getIdCampeonato())
                .orElseThrow(() -> new ResourceNotFoundException("Campeonato no encontrado: " + dto.getIdCampeonato()));

        equipo.setNombre(dto.getNombre());
        equipo.setRepresentante(dto.getRepresentante());
        equipo.setFechaCreacion(dto.getFechaCreacion());
        equipo.setCampeonato(campeonato);

        return toDTO(equipoRepository.save(equipo));
    }

    public void delete(Long id) {
        if (!equipoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipo no encontrado: " + id);
        }
        equipoRepository.deleteById(id);
    }


    private EquipoResponseDTO toDTO(Equipo e) {
        EquipoResponseDTO dto = new EquipoResponseDTO();
        dto.setIdEquipo(e.getIdEquipo());
        dto.setNombre(e.getNombre());
        dto.setRepresentante(e.getRepresentante());
        dto.setFechaCreacion(e.getFechaCreacion().toString());

        EquipoResponseDTO.CampeonatoInfo info = new EquipoResponseDTO.CampeonatoInfo();
        info.setId(e.getCampeonato().getId());
        info.setNombre(e.getCampeonato().getNombre());
        dto.setCampeonato(info);

        return dto;
    }

    private Equipo toEntity(EquipoRequestDTO dto, Campeonato campeonato) {
        Equipo equipo = new Equipo();
        equipo.setNombre(dto.getNombre());
        equipo.setRepresentante(dto.getRepresentante());
        equipo.setFechaCreacion(dto.getFechaCreacion());
        equipo.setCampeonato(campeonato);
        return equipo;
    }
}