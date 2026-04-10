package com.idat.campeonato_backend.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PartidoRequestDTO {
    private Long equipoLocalId;
    private Long equipoVisitanteId;
    private Integer golesLocal;
    private Integer golesVisitante;
    private LocalDate fecha;
    private Long idCampeonato;
}