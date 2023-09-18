package com.accenture.academico.bank.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "numero", "agencia_id" })
})
@DiscriminatorValue("conta_corrente")
public class ContaCorrente extends Conta {

    public ContaCorrente() {
        super();
    }

    public ContaCorrente(Long id, String numero, Cliente cliente, Agencia agencia) {
        super(id, numero, cliente, agencia);
    }

    public ContaCorrente(String numero, Cliente cliente, Agencia agencia) {
        super(numero, cliente, agencia);
    }
}
