package com.accenture.academico.bank.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Agencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 4, unique = true)
    @Pattern(regexp = "^[0-9]+$", message = "O número da agência deve conter apenas dígitos.")
    @Size(min = 4, max = 4, message = "O número da agência deve ter exatamente 4 dígitos.")
    @NotNull(message = "Número da agencia não informado.")
    private String numAgencia;

	@Column(nullable = false, length = 255, unique = true)
    private String nomeAgencia;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    @Column(nullable = false, length = 16)
    private String telefone;

    public Agencia() { }
    
    public Agencia(String numAgencia, String nomeAgencia, Endereco endereco, String telefone) {
        this.numAgencia = numAgencia;
        this.nomeAgencia = nomeAgencia;
        this.endereco = endereco;
        this.telefone = telefone;
    }
    
    public Agencia(Long id, String numAgencia, String nomeAgencia, Endereco endereco, String telefone) {
        this.id = id;
        this.numAgencia = numAgencia;
        this.nomeAgencia = nomeAgencia;
        this.endereco = endereco;
        this.telefone = telefone;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumAgencia() {
        return numAgencia;
    }

    public void setNumAgencia(String numAgencia) {
        this.numAgencia = numAgencia;
    }

    public String getNomeAgencia() {
        return nomeAgencia;
    }

    public void setNomeAgencia(String nomeAgencia) {
        this.nomeAgencia = nomeAgencia;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((numAgencia == null) ? 0 : numAgencia.hashCode());
        result = prime * result + ((nomeAgencia == null) ? 0 : nomeAgencia.hashCode());
        result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
        result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
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
        Agencia other = (Agencia) obj;
        if (id != other.id)
            return false;
        if (numAgencia == null) {
            if (other.numAgencia != null)
                return false;
        } else if (!numAgencia.equals(other.numAgencia))
            return false;
        if (nomeAgencia == null) {
            if (other.nomeAgencia != null)
                return false;
        } else if (!nomeAgencia.equals(other.nomeAgencia))
            return false;
        if (endereco == null) {
            if (other.endereco != null)
                return false;
        } else if (!endereco.equals(other.endereco))
            return false;
        if (telefone == null) {
            if (other.telefone != null)
                return false;
        } else if (!telefone.equals(other.telefone))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Agencia [id=" + id + ", numAgencia=" + numAgencia + ", nomeAgencia=" + nomeAgencia + ", endereco="
                + endereco + ", telefone=" + telefone + "]";
    }

}
