package com.accenture.academico.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.academico.bank.model.Extrato;     
import com.accenture.academico.bank.repository.ExtratoRepository;

@Service
public class ExtratoService {
    
    @Autowired
    ExtratoRepository extratoRepository;

    public Extrato save(Extrato extrato) {
        return extratoRepository.save(extrato);
    }

    // public List<Extrato> listExtratoByConta(Long contaId, LocalDate dtInicio, LocalDate dtFim) {
    //     return extratoRepository.findByConta(contaId, dtInicio, dtFim);
    // }

}
