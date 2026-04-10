package com.idat.campeonato_backend.dto;

import java.time.LocalDate; 

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CampeonatoResponseDTO {
    private Long id;
    private String nombre;
    private LocalDate fechaInicio;
    private Integer cantidadEquipos;
}