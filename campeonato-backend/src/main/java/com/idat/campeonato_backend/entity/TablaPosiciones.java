package com.idat.campeonato_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = { "equipo", "campeonato" }) 
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tabla_posiciones", uniqueConstraints = {
        @UniqueConstraint(name = "uq_equipo_campeonato", columnNames = { "equipo_id", "campeonato_id" })
})
public class TablaPosiciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipo equipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campeonato_id", nullable = false)
    private Campeonato campeonato;

    private Integer pj;
    private Integer g;
    private Integer e;
    private Integer p;
    private Integer gf;
    private Integer gc;
    private Integer pts;
}