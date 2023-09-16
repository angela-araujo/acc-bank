package com.accenture.academico.bank.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.accenture.academico.bank.model.ContaCorrente;
import com.accenture.academico.bank.model.Extrato;

@Repository
public interface ExtratoRepository extends JpaRepository<Extrato, Long> {

    @Query("SELECT e FROM Extrato e WHERE e.contaCorrente = ?1 AND e.dataMovimento BETWEEN ?2 AND ?3")
    List<Extrato> buscarExtrato(ContaCorrente conta, LocalDateTime dtInicio, LocalDateTime dtFim);
    
}
