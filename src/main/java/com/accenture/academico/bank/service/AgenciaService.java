package com.accenture.academico.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.academico.bank.model.Agencia;
import com.accenture.academico.bank.repository.AgenciaRepository;

import jakarta.transaction.Transactional;

@Service
public class AgenciaService {

    @Autowired
    AgenciaRepository agenciaRepository;

    public List<Agencia> getAllAgencia() {
        return agenciaRepository.findAll();
    }

    @Transactional
    public Agencia saveOrUpdateAgencia(Agencia agencia) {
        return agenciaRepository.save(agencia);
    }
    
    public void deleteAgencia(long id) {
        agenciaRepository.deleteById(id);
    }
    
    public Agencia getAgenciaByNumAgencia(String numAgencia) {
        return agenciaRepository.findByNumAgencia(numAgencia);
    }

    public List<Agencia> getAgenciaByName(String nomeAgencia) {
        return agenciaRepository.findByPartialName(nomeAgencia);
    }
    
    public Agencia getAgenciaById(long id) {
        Agencia agencia = agenciaRepository.findById(id).get();

        if (agenciaRepository.findById(id).isPresent()) {
            return agencia;
        }

        return null;
    }

}
