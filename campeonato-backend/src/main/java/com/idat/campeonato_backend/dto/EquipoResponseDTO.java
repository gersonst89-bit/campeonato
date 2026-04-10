package com.idat.campeonato_backend.dto;

import lombok.Data;

@Data
public class EquipoResponseDTO {
    private Long idEquipo;
    private String nombre;
    private String representante;
    private String fechaCreacion;
    private Long idCampeonato;
    private CampeonatoInfo campeonato;

    @Data
    public static class CampeonatoInfo {
        private Long id;
        private String nombre;
    }
}