package com.accenture.academico.bank.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@MappedSuperclass
public abstract class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(length = 10)
    @Pattern(regexp = "^[0-9]+$", message = "O número da conta deve conter apenas dígitos.")
    @Size(min = 1, max = 10, message = "O número da conta deve ter no mínio 1 dígito e no máximo 10 dígitos.")
    protected String numero;

    @ManyToOne
    protected Cliente cliente;

    @ManyToOne
    protected Agencia agencia;

    @Column
    protected BigDecimal saldo;

    public Conta() {
    }

    public Conta(Long id, String numero, Cliente cliente, Agencia agencia) {
        this.id = id;
        this.numero = numero;
        this.cliente = cliente;
        this.agencia = agencia;
    }

    public Conta(String numero, Cliente cliente, Agencia agencia) {
        setNumero(numero);
        this.cliente = cliente;
        this.agencia = agencia;
    }

    @PrePersist
    public void prePersist() {
        saldo = BigDecimal.ZERO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        // Verifica se o número é nulo ou vazio
        if (numero == null || numero.isEmpty()) {
            // Defina o número como uma string de 10 zeros
            this.numero = "0000000000";
        } else {
            // Remove qualquer caractere não numérico
            numero = numero.replaceAll("[^0-9]", "");

            // Se o número tiver menos de 10 dígitos, preencha com zeros à esquerda
            if (numero.length() < 10) {
                this.numero = String.format("%010d", Long.parseLong(numero));
            } else {
                this.numero = numero;
            }
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Conta [id=" + id + ", numero=" + numero + ", cliente=" + cliente + ", agencia=" + agencia + ", saldo="
                + saldo + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((numero == null) ? 0 : numero.hashCode());
        result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
        result = prime * result + ((agencia == null) ? 0 : agencia.hashCode());
        result = prime * result + ((saldo == null) ? 0 : saldo.hashCode());
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
        Conta other = (Conta) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (numero == null) {
            if (other.numero != null)
                return false;
        } else if (!numero.equals(other.numero))
            return false;
        if (cliente == null) {
            if (other.cliente != null)
                return false;
        } else if (!cliente.equals(other.cliente))
            return false;
        if (agencia == null) {
            if (other.agencia != null)
                return false;
        } else if (!agencia.equals(other.agencia))
            return false;
        if (saldo == null) {
            if (other.saldo != null)
                return false;
        } else if (!saldo.equals(other.saldo))
            return false;
        return true;
    }

}
