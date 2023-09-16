package com.accenture.academico.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.accenture.academico.bank.model.Agencia;
import com.accenture.academico.bank.model.ContaCorrente;

@Repository
public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Long> {

    @Query("SELECT c FROM ContaCorrente c WHERE agencia = ?1 AND numero = ?2")
    ContaCorrente findByAgenciaAndNumero(Agencia agencia, String numero);

}
