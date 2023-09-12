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
import com.accenture.academico.bank.model.Cliente;
import com.accenture.academico.bank.service.ClienteService;

@RestController
@RequestMapping("/api/v1/cliente")
public class ClienteController {

    private static Logger logger = LoggerFactory.getLogger(BankApplication.class);

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> getAll() {
        return clienteService.getAllCliente();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable(value = "id") Long id) {
        try {
            logger.info(":: ClienteController.getById :: id = " + id);
            Cliente cliente = clienteService.getClienteById(id);
            return ResponseEntity.ok().body(cliente);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/name/{nome}")
    public List<Cliente> getByNome(@PathVariable(value = "nome") String nome) {
        return clienteService.getClienteByName(nome);
    }
    
    @GetMapping("/cpf/{cpf}")
    public Cliente getClienteByCPF(@PathVariable(value = "cpf") String cpf) {
        return clienteService.getClienteByCPF(cpf);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody Cliente cliente) {
        try {
            Cliente newCliente = clienteService.saveOrUpdateCliente(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCliente);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(":: Erro: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            clienteService.deleteCliente(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente excluído com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(":: Erro: " + e.getMessage());
        }
    }

    

}
