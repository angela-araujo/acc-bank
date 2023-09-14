package com.accenture.academico.bank.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.academico.bank.BankApplication;
import com.accenture.academico.bank.model.Agencia;
import com.accenture.academico.bank.repository.AgenciaRepository;

import jakarta.transaction.Transactional;

@Service
public class AgenciaService {

    private static Logger logger = LoggerFactory.getLogger(BankApplication.class);

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
    
    public List<Agencia> getAgenciaByNumAgencia(String numAgencia) {
        return agenciaRepository.findByP (numAgencia);
    }

    public List<Agencia> getAgenciaByName(String nomeAgencia) {
        return agenciaRepository.findByPartialName(nomeAgencia);
    }
    
    public Agencia getAgenciaById(long id) {
        Agencia agencia = agenciaRepository.findById(id).get();
        logger.info(":: [AgenciaService.getAgenciaById] - agência: " + agencia);

        if (agenciaRepository.findById(id).isPresent()) {
            logger.info(":: [AgenciaService.getAgenciaById]" + agencia);
            return agencia;
        }

        return null;
    }

}
