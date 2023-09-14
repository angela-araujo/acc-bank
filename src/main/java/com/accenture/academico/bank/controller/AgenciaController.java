package com.accenture.academico.bank.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.academico.bank.BankApplication;
import com.accenture.academico.bank.model.Agencia;
import com.accenture.academico.bank.service.AgenciaService;

@RestController
@RequestMapping("/api/v1/agencia")

public class AgenciaController {

    private static Logger logger = LoggerFactory.getLogger(BankApplication.class);

    @Autowired
    private AgenciaService agenciaService;

    @GetMapping
    public List<Agencia> getAll() {
        return agenciaService.getAllAgencia();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agencia> getById(@PathVariable(value = "id") Long id) {
        try {
            logger.info(":: AgenciaController.getById :: id = " + id);
            Agencia agencia = agenciaService.getAgenciaById(id);
            return ResponseEntity.ok().body(agencia);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/numagencia/{numAgencia}")
    public ResponseEntity<Object> getAgenciaByNumAgencia(@PathVariable(value = "numAgencia") String numAgencia) {
        try {            
            Agencia agencia = agenciaService.getAgenciaByNumAgencia(numAgencia);
            if (agencia == null) {
                throw new Exception("Agencia não encontrada.");
            }
            return ResponseEntity.ok().body(agencia);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/name/{nomeAgencia}")
    public ResponseEntity<Object> getAgenciaByNomeAgencia(@PathVariable(value = "nomeAgencia") String nomeAgencia) {
        try {
            List<Agencia> agencias = agenciaService.getAgenciaByName(nomeAgencia);
            logger.info(":: AgenciaController.getById :: agencias = " + agencias);
            if (agencias.isEmpty()) {
                throw new Exception("Nome de agencia não encontrado");
            }
            return ResponseEntity.ok().body(agencias);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody Agencia agencia) {
        try {
            Agencia newAgencia = agenciaService.saveOrUpdateAgencia(agencia);
            return ResponseEntity.status(HttpStatus.CREATED).body(newAgencia);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(":: Erro: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            agenciaService.deleteAgencia(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Agencia excluída com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(":: Erro: " + e.getMessage());
        }
    }

}
