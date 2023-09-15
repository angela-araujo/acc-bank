package com.accenture.academico.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.academico.bank.model.ContaCorrente;

@Repository
public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Long> {

    // @Query("SELECT c FROM conta_corrente c WHERE agencia_id = ?1 AND numero = ?2")
    // ContaCorrente findByAgenciaIdAndNumero(Long agenciaId, String numero);

}
