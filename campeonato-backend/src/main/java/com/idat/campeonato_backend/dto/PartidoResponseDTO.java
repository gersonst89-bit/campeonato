package com.idat.campeonato_backend.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PartidoResponseDTO {
    private Long id;
    private Long idCampeonato;       
    private String campeonatoNombre;
    private Long equipoLocalId;       
    private String equipoLocalNombre;
    private Long equipoVisitanteId;   
    private String equipoVisitanteNombre;
    private Integer golesLocal;
    private Integer golesVisitante;
    private LocalDate fecha;
}