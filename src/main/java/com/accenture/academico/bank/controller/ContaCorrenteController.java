package com.accenture.academico.bank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.academico.bank.BankApplication;
import com.accenture.academico.bank.model.ContaCorrente;
import com.accenture.academico.bank.service.ContaCorrenteService;

@RestController
@RequestMapping("/api/v1/contacorrente")
public class ContaCorrenteController {
    
    private static Logger logger = LoggerFactory.getLogger(BankApplication.class);

    @Autowired
    private ContaCorrenteService contaCorrenteService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody ContaCorrente conta) {
        try {
            ContaCorrente newConta = contaCorrenteService.save(conta);
            logger.info("::Criando conta...");
            return ResponseEntity.status(HttpStatus.CREATED).body(newConta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
