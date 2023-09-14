package com.accenture.academico.bank.model;

import java.math.BigDecimal;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("conta_corrente")
public class ContaCorrente extends Conta {

    public ContaCorrente(Long id, String numero, Cliente cliente, Agencia agencia, BigDecimal saldo) {
        super(id, numero, cliente, agencia, saldo);
    }

}
