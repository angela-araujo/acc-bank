package com.accenture.academico.bank.model;

import java.math.BigDecimal;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.transaction.Transactional;

@Entity
@DiscriminatorValue("conta_corrente")
public class ContaCorrente extends Conta {

    @Override
    public BigDecimal sacar(BigDecimal valor) throws Exception {
        if (valor.compareTo(saldo) > 0) {
            throw new Exception("Saldo insuficiente!");
        }
        
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Valor deve ser maior que zero.");
        }

        saldo = saldo.subtract(valor);

        registrarExtrato(Operacao.SAQUE, valor, null);

        return getSaldo();
    }

    @Override
    public void depositar(BigDecimal valor) throws Exception {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Valor deve ser maior que zero.");
        }

        saldo = saldo.add(valor);

        registrarExtrato(Operacao.DEPOSITO, valor, null);
    }

    @Override
    @Transactional
    public void transferir(Conta contaDestino, BigDecimal valor) throws Exception {
        if (valor.compareTo(saldo) > 0) {
            throw new Exception("Saldo insuficiente!");
        }
        
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Valor deve ser maior que zero.");
        }

        saldo = saldo.subtract(valor);
        contaDestino.depositar(valor);

        registrarExtrato(Operacao.TRANSFENCIA, valor, "Conta destino: " + contaDestino);
    }

    @Override
    public void registrarExtrato(Operacao Operacao, BigDecimal valor, String descricao) {        
        throw new UnsupportedOperationException("Unimplemented method 'registrarExtrato'");
    }
    
}
