package com.accenture.academico.bank.interfaces;

import java.math.BigDecimal;

import com.accenture.academico.bank.model.Conta;
import com.accenture.academico.bank.model.Operacao;

public interface IConta {
    public BigDecimal sacar(Conta conta, BigDecimal valor) throws Exception;

    public void depositar(Conta conta, BigDecimal valor) throws Exception;

    public void transferir(Conta contaOrigem, Conta contaDestino, BigDecimal valor) throws Exception;

    public void registrarExtrato(Conta conta, Operacao Operacao, BigDecimal valor, String descricao) throws Exception;
}
