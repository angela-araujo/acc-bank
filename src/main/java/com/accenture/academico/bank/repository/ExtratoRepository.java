package com.accenture.academico.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.academico.bank.model.Extrato;

@Repository
public interface ExtratoRepository extends JpaRepository<Extrato, Long> {

    // @Query("SELECT e FROM extrato e WHERE e.conta_corrente_id = ?1 AND e.dt_movimento BETWEEN ?2 AND ?3")
    // List<Extrato> findByConta(Long contaId, LocalDate dtInicio, LocalDate dtFim);
    
}
