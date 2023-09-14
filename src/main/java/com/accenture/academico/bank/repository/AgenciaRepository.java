package com.accenture.academico.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.academico.bank.model.Agencia;

@Repository
public interface AgenciaRepository extends JpaRepository<Agencia, Long>{
    @Query("SELECT a FROM Agencia a WHERE a.nomeAgencia LIKE %:partialName%")
    List<Agencia> findByPartialName(@Param("partialName") String partialName);

    Agencia findByNumAgencia(String numAgencia);
}
