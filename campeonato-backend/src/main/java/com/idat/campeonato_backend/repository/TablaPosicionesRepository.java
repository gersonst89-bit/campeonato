package com.idat.campeonato_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idat.campeonato_backend.entity.TablaPosiciones;

@Repository
public interface TablaPosicionesRepository extends JpaRepository<TablaPosiciones, Long> {

    List<TablaPosiciones> findByCampeonatoIdOrderByPtsDescGfDesc(Long campeonatoId);
}