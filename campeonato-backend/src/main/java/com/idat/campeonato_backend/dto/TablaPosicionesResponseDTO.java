
package com.idat.campeonato_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TablaPosicionesResponseDTO {

    private Long idEquipo;
    private String nombreEquipo;
    private Integer pj; 
    private Integer g; 
    private Integer e; 
    private Integer p; 
    private Integer gf; 
    private Integer gc; 
    private Integer pts; 


    public Integer getDiferenciaGoles() {
        return (gf != null && gc != null) ? gf - gc : 0;
    }
}