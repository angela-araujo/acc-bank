package com.accenture.academico.bank.dto;

public class ContaCorrenteDTO {

    private String numero;
    private Long clienteId;
    private Long agenciaId;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getAgenciaId() {
        return agenciaId;
    }

    public void setAgenciaId(Long agenciaId) {
        this.agenciaId = agenciaId;
    }

    public ContaCorrenteDTO() {
    }

    public ContaCorrenteDTO(String numero, Long clienteId, Long agenciaId) {
        this.numero = numero;
        this.clienteId = clienteId;
        this.agenciaId = agenciaId;
    }

}
