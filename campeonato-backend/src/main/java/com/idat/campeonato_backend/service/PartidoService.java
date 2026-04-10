package com.idat.campeonato_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.campeonato_backend.dto.PartidoRequestDTO;
import com.idat.campeonato_backend.dto.PartidoResponseDTO;
import com.idat.campeonato_backend.dto.TablaPosicionesResponseDTO;
import com.idat.campeonato_backend.entity.Campeonato;
import com.idat.campeonato_backend.entity.Equipo;
import com.idat.campeonato_backend.entity.Partido;
import com.idat.campeonato_backend.entity.TablaPosiciones;
import com.idat.campeonato_backend.exception.ResourceNotFoundException;
import com.idat.campeonato_backend.repository.CampeonatoRepository;
import com.idat.campeonato_backend.repository.EquipoRepository;
import com.idat.campeonato_backend.repository.PartidoRepository;
import com.idat.campeonato_backend.repository.TablaPosicionesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartidoService {

    private final PartidoRepository partidoRepository;
    private final EquipoRepository equipoRepository;
    private final CampeonatoRepository campeonatoRepository;
    private final TablaPosicionesRepository tablaPosicionesRepository;

    public List<PartidoResponseDTO> findAll() {
        return partidoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public PartidoResponseDTO findById(Long id) {
        return toDTO(partidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partido no encontrado: " + id)));
    }

    public List<PartidoResponseDTO> findByCampeonato(Long campeonatoId) {
        return partidoRepository.findByCampeonatoId(campeonatoId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public PartidoResponseDTO save(PartidoRequestDTO dto) {
        validarEquiposDiferentes(dto);

        Equipo local = findEquipo(dto.getEquipoLocalId(), "local");
        Equipo visitante = findEquipo(dto.getEquipoVisitanteId(), "visitante");
        Campeonato campeonato = findCampeonato(dto.getIdCampeonato());

        Partido partido = new Partido();
        aplicarDatos(partido, dto, local, visitante, campeonato);

        return toDTO(partidoRepository.save(partido));
    }

    @Transactional
    public PartidoResponseDTO update(Long id, PartidoRequestDTO dto) {
        validarEquiposDiferentes(dto);

        Partido partido = partidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partido no encontrado: " + id));

        Equipo local = findEquipo(dto.getEquipoLocalId(), "local");
        Equipo visitante = findEquipo(dto.getEquipoVisitanteId(), "visitante");
        Campeonato campeonato = findCampeonato(dto.getIdCampeonato());

        aplicarDatos(partido, dto, local, visitante, campeonato);

        return toDTO(partidoRepository.save(partido));
    }

    public void delete(Long id) {
        if (!partidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Partido no encontrado: " + id);
        }
        partidoRepository.deleteById(id);
    }

    public List<TablaPosicionesResponseDTO> getTablaPosPorCampeonato(Long campeonatoId) {
        return tablaPosicionesRepository
                .findByCampeonatoIdOrderByPtsDescGfDesc(campeonatoId)
                .stream()
                .map(this::toDTOTabla)
                .toList();
    }


    private Equipo findEquipo(Long id, String tipo) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo " + tipo + " no encontrado: " + id));
    }

    private Campeonato findCampeonato(Long id) {
        return campeonatoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campeonato no encontrado: " + id));
    }

    private void validarEquiposDiferentes(PartidoRequestDTO dto) {
        if (dto.getEquipoLocalId().equals(dto.getEquipoVisitanteId())) {
            throw new IllegalArgumentException("El equipo local y visitante no pueden ser el mismo");
        }
    }

    private void aplicarDatos(Partido p, PartidoRequestDTO dto,
            Equipo local, Equipo visitante, Campeonato campeonato) {
        p.setEquipoLocal(local);
        p.setEquipoVisitante(visitante);
        p.setGolesLocal(dto.getGolesLocal());
        p.setGolesVisitante(dto.getGolesVisitante());
        p.setFecha(dto.getFecha());
        p.setCampeonato(campeonato);
    }


    private PartidoResponseDTO toDTO(Partido p) {
        PartidoResponseDTO dto = new PartidoResponseDTO();
        dto.setId(p.getId());
        dto.setIdCampeonato(p.getCampeonato().getId());
        dto.setCampeonatoNombre(p.getCampeonato().getNombre());
        dto.setEquipoLocalId(p.getEquipoLocal().getIdEquipo());
        dto.setEquipoLocalNombre(p.getEquipoLocal().getNombre());
        dto.setEquipoVisitanteId(p.getEquipoVisitante().getIdEquipo());
        dto.setEquipoVisitanteNombre(p.getEquipoVisitante().getNombre());
        dto.setGolesLocal(p.getGolesLocal());
        dto.setGolesVisitante(p.getGolesVisitante());
        dto.setFecha(p.getFecha());
        return dto;
    }

    private TablaPosicionesResponseDTO toDTOTabla(TablaPosiciones t) {
        TablaPosicionesResponseDTO dto = new TablaPosicionesResponseDTO();
        dto.setIdEquipo(t.getEquipo().getIdEquipo());
        dto.setNombreEquipo(t.getEquipo().getNombre());
        dto.setPj(t.getPj());
        dto.setG(t.getG());
        dto.setE(t.getE());
        dto.setP(t.getP());
        dto.setGf(t.getGf());
        dto.setGc(t.getGc());
        dto.setPts(t.getPts());
        return dto;
    }
}