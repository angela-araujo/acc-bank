package com.accenture.academico.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.accenture.academico.bank.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    @Query("SELECT c FROM Cliente c WHERE c.nome LIKE %:partialName%")
    List<Cliente> findByPartialName(@Param("partialName") String partialName);

    Cliente findByCpf(String cpf);

}
