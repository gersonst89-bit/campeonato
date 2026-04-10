
package com.idat.campeonato_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idat.campeonato_backend.entity.Equipo;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    List<Equipo> findByCampeonatoId(Long campeonatoId);

    long countByCampeonatoId(Long campeonatoId);

}