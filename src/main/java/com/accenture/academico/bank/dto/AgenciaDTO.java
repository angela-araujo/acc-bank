package com.accenture.academico.bank.dto;

public class AgenciaDTO {
    private String numAgencia;
    private String nomeAgencia;
    private EnderecoDTO endereco;
    private String telefone;

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

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public AgenciaDTO() {
    }

    public AgenciaDTO(String numAgencia, String nomeAgencia, EnderecoDTO endereco, String telefone) {
        this.numAgencia = numAgencia;
        this.nomeAgencia = nomeAgencia;
        this.endereco = endereco;
        this.telefone = telefone;
    }

}
