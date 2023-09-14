package com.accenture.academico.bank.interfaces;

import java.math.BigDecimal;

import com.accenture.academico.bank.model.Conta;
import com.accenture.academico.bank.model.Operacao;

public interface IConta {
    public BigDecimal sacar(BigDecimal valor) throws Exception;

    public void depositar(BigDecimal valor) throws Exception;

    public void transferir(Conta contaDestino, BigDecimal valor) throws Exception;

    public void registrarExtrato(Operacao Operacao, BigDecimal valor, String descricao) throws Exception;
}
