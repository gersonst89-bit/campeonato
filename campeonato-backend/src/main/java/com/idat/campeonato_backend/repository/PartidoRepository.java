package com.idat.campeonato_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idat.campeonato_backend.entity.Partido;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {
    List<Partido> findByCampeonatoId(Long campeonatoId);
}
