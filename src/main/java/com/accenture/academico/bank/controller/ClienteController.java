package com.accenture.academico.bank.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

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
    public ResponseEntity<Object> getByNome(@PathVariable(value = "nome") String nome) {
        try {
            List<Cliente> clientes = clienteService.getClienteByName(nome);

            if (clientes.isEmpty()) {
                throw new Exception("Cliente não encontrado");
            }
            return ResponseEntity.ok().body(clientes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Object> getClienteByCPF(@PathVariable(value = "cpf") String cpf) {
        try {
            Cliente cliente = clienteService.getClienteByCPF(cpf);
            if (cliente == null) {
                throw new Exception("CPF não encontrado");
            }
            return ResponseEntity.ok().body(cliente);            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody Cliente cliente) {
        try {
            Cliente newCliente = clienteService.saveOrUpdateCliente(cliente);

            return ResponseEntity.status(HttpStatus.CREATED).body(newCliente);
            
        } catch (ConstraintViolationException e) {

            List<String> msgErro = new ArrayList<>();
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            for (ConstraintViolation<?> violation : violations) {
                String propertyPath = violation.getPropertyPath().toString();
                if (propertyPath.equals("cpf")) {
                    return ResponseEntity.badRequest().body("CPF inválido.");
                }
                String message = violation.getMessage();
                msgErro.add("Erro de validação em " + propertyPath + ": " + message);
            }
            return ResponseEntity.badRequest().body(msgErro);

        } catch (Exception e) {
            logger.error("::ClienteController:: save()\nErrorMessage: " + e.getMessage() +
                    "\nErrorCause: " + e.getCause());
            return ResponseEntity.badRequest().body(":: Erro: " + e.getMessage() + " \n:: Causa: " + e.getCause());
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
