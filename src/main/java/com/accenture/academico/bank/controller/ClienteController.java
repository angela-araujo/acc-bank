package com.accenture.academico.bank.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/cliente")
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

    @GetMapping("/{nome}")
    public List<Cliente> getByNome(@PathVariable(value = "nome") String nome) {
        return clienteService.getClienteByName(nome);
    }

    @PostMapping()
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {
        try {
            Cliente newCliente = clienteService.saveOrUpdateCliente(cliente);
            return ResponseEntity.ok().body(newCliente);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
