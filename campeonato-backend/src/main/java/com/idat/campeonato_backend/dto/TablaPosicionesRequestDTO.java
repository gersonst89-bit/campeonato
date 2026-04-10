
package com.idat.campeonato_backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TablaPosicionesRequestDTO {

    @NotNull
    private Long equipoId;

    @NotNull
    private Long campeonatoId;

    @NotNull
    @Min(0)
    private Integer pj;
    @NotNull
    @Min(0)
    private Integer g;
    @NotNull
    @Min(0)
    private Integer e;
    @NotNull
    @Min(0)
    private Integer p;
    @NotNull
    @Min(0)
    private Integer gf;
    @NotNull
    @Min(0)
    private Integer gc;
    @NotNull
    @Min(0)
    private Integer pts;
}