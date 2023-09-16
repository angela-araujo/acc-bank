package com.accenture.academico.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Extrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected LocalDateTime dataMovimento;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Operacao operacao;
    
    @Column
    protected BigDecimal valor;
        
    @Column
    protected BigDecimal saldoAtual;

    @ManyToOne
    protected ContaCorrente contaCorrente;
        
    @Column(length = 255)
    protected String descricao;

    public Extrato() {
    }

    public Extrato(Long id, LocalDateTime dataMovimento, Operacao operacao, BigDecimal valor, BigDecimal saldoAtual,
            ContaCorrente conta, String descricao) {
        this.id = id;
        this.dataMovimento = dataMovimento;
        this.operacao = operacao;
        this.valor = valor;
        this.saldoAtual = saldoAtual;
        this.contaCorrente = conta;
        this.descricao = descricao;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataMovimento() {
        return dataMovimento;
    }


    public void setDataMovimento(LocalDateTime dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(BigDecimal saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public ContaCorrente getConta() {
        return contaCorrente;
    }

    public void setConta(ContaCorrente conta) {
        this.contaCorrente = conta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((dataMovimento == null) ? 0 : dataMovimento.hashCode());
        result = prime * result + ((operacao == null) ? 0 : operacao.hashCode());
        result = prime * result + ((valor == null) ? 0 : valor.hashCode());
        result = prime * result + ((saldoAtual == null) ? 0 : saldoAtual.hashCode());
        result = prime * result + ((contaCorrente == null) ? 0 : contaCorrente.hashCode());
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Extrato other = (Extrato) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (dataMovimento == null) {
            if (other.dataMovimento != null)
                return false;
        } else if (!dataMovimento.equals(other.dataMovimento))
            return false;
        if (operacao != other.operacao)
            return false;
        if (valor == null) {
            if (other.valor != null)
                return false;
        } else if (!valor.equals(other.valor))
            return false;
        if (saldoAtual == null) {
            if (other.saldoAtual != null)
                return false;
        } else if (!saldoAtual.equals(other.saldoAtual))
            return false;
        if (contaCorrente == null) {
            if (other.contaCorrente != null)
                return false;
        } else if (!contaCorrente.equals(other.contaCorrente))
            return false;
        if (descricao == null) {
            if (other.descricao != null)
                return false;
        } else if (!descricao.equals(other.descricao))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Extrato [id=" + id + ", dataMovimento=" + dataMovimento + ", operacao=" + operacao + ", valor=" + valor
                + ", saldoAtual=" + saldoAtual + ", conta=" + contaCorrente + ", descricao=" + descricao + "]";
    }

    

    
}
