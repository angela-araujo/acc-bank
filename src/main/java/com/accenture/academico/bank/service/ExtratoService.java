package com.accenture.academico.bank.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.academico.bank.model.ContaCorrente;
import com.accenture.academico.bank.model.Extrato;     
import com.accenture.academico.bank.repository.ExtratoRepository;

@Service
public class ExtratoService {
    
    @Autowired
    ExtratoRepository extratoRepository;

    public Extrato save(Extrato extrato) {
        return extratoRepository.save(extrato);
    }

    public List<Extrato> listExtratoByConta(Long contaId, LocalDate dtInicio, LocalDate dtFim) {
        return null;
    }

    public List<Extrato> buscarExtrato(ContaCorrente conta, LocalDateTime dtInicio, LocalDateTime dtFim) {

        return extratoRepository.buscarExtrato(conta, dtInicio, dtFim);
    }


}
