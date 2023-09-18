package com.accenture.academico.bank.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.academico.bank.BankApplication;
import com.accenture.academico.bank.dto.ContaCorrenteDTO;
import com.accenture.academico.bank.model.Agencia;
import com.accenture.academico.bank.model.Cliente;
import com.accenture.academico.bank.model.ContaCorrente;
import com.accenture.academico.bank.model.Extrato;
import com.accenture.academico.bank.service.AgenciaService;
import com.accenture.academico.bank.service.ClienteService;
import com.accenture.academico.bank.service.ContaCorrenteService;
import com.accenture.academico.bank.service.ExtratoService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Conta Corrente", description = "Operações relacionadas a contas correntes")
@RestController
@RequestMapping("/api/v1/contacorrente")
public class ContaCorrenteController {

    private static Logger logger = LoggerFactory.getLogger(BankApplication.class);

    @Autowired
    private ContaCorrenteService contaCorrenteService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AgenciaService agenciaService;

    @Autowired
    private ExtratoService extratoService;

    @GetMapping("/{id}")
    public ResponseEntity<ContaCorrente> getById(@PathVariable(value = "id") Long id) {
        try {
            ContaCorrente contaCorrente = contaCorrenteService.getContaById(id);
            return ResponseEntity.ok().body(contaCorrente);
        } catch (Exception e) {
            logger.error("\n::ContaCorrenteController:: getById()\nErrorMessage: " + e.getMessage() +
                "\nErrorCause: " + e.getCause());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody ContaCorrenteDTO contaDTO) {
        try {

            Cliente cliente = clienteService.getClienteById(contaDTO.getClienteId());
            Agencia agencia = agenciaService.getAgenciaById(contaDTO.getAgenciaId());
            ContaCorrente conta = new ContaCorrente(contaDTO.getNumero(), cliente, agencia);

            ContaCorrente newConta = contaCorrenteService.save(conta);
            return ResponseEntity.status(HttpStatus.CREATED).body(newConta);

        } catch (DataIntegrityViolationException e) {
            logger.error("::ContaCorrenteController:: save()\nErrorMessage: " + e.getMessage() +
                "\nErrorCause: " + e.getCause());
            if (e.getCause() instanceof ConstraintViolationException) {
                String mensagemDeErro = "Erro ao criar Conta. Já existe uma conta com o mesmo número e agência.";
                return ResponseEntity.badRequest().body(mensagemDeErro);
            } else {
                return ResponseEntity.badRequest().body("Erro de integridade de dados: " + e.getMessage());
            }
        } catch (Exception e) {
            logger.error("::ContaCorrenteController:: save()\nErrorMessage: " + e.getMessage() +
                "\nErrorCause: " + e.getCause());
            return ResponseEntity.badRequest()
                    .body("MessageErro: " + e.getMessage() + "\n\nCausaErro: " + e.getCause());
        }
    }

    @PostMapping("/{contaId}/saque/{valor}")
    public ResponseEntity<Object> sacar(@PathVariable(value = "contaId") Long contaId,
            @PathVariable(value = "valor") BigDecimal valor) throws Exception {
        try {
            System.out.println(":: contaId:" + contaId + " | valor:" + valor);
            ContaCorrente conta = contaCorrenteService.getContaById(contaId);
            BigDecimal saldo = contaCorrenteService.sacar(conta, valor);
            return ResponseEntity.ok().body("saldo: " + saldo);
        } catch (Exception e) {
            logger.error("::ContaCorrenteController:: sacar()\nErrorMessage: " + e.getMessage() +
                "\nErrorCause: " + e.getCause());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{contaId}/deposito/{valor}")
    public ResponseEntity<String> depositar(@PathVariable(value = "contaId") Long contaId,
            @PathVariable(value = "valor") BigDecimal valor) throws Exception {

        try {
            System.out.println("contaId: " + contaId + " | valor: " + valor);
            ContaCorrente conta = contaCorrenteService.getContaById(contaId);
            contaCorrenteService.depositar(conta, valor);
            return ResponseEntity.ok().body("Depósito realizado com sucesso");
        } catch (Exception e) {
            logger.error("::ContaCorrenteController:: depositar()\nErrorMessage: " + e.getMessage() +
                "\nErrorCause: " + e.getCause());
            return ResponseEntity.badRequest().body(e.getCause() + " \nMensagemErro: " + e.getMessage());
        }
    }

    @PostMapping("/{contaOrigemId}/transferencia/{contaDestinoId}/{valor}")    
    public ResponseEntity<String> transferir(
            @PathVariable(value = "contaOrigemId") Long contaOrigemId,
            @PathVariable(value = "contaDestinoId") Long contaDestinoId,
            @PathVariable(value = "valor") BigDecimal valor) throws Exception {
        try {
            
            ContaCorrente contaOrigem = contaCorrenteService.getContaById(contaOrigemId);
            ContaCorrente contaDestino = contaCorrenteService.getContaById(contaDestinoId);
    
            contaCorrenteService.transferir(contaOrigem, contaDestino, valor);
           
            return ResponseEntity.ok().body("Transferência realizada com sucesso.");

        } catch (Exception e) {
            logger.error("\n::ContaCorrenteController:: transferir()\nErrorMessage: " + e.getMessage() +
                "\nErrorCause: " + e.getCause());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<List<Extrato>> extrato(@PathVariable(value = "id") Long id) {
        try {
            ContaCorrente conta = contaCorrenteService.getContaById(id);

            LocalDateTime dtInicio = LocalDate.now().minusDays(7).atTime(0, 0, 0, 0);
            LocalDateTime dtFim = LocalDate.now().atTime(23, 59, 59, 999999999);
            
            logger.info("\ndtInicio: " + dtInicio + "\ndtFim: " + dtFim);
            
            List<Extrato> extrato = extratoService.buscarExtrato(conta, dtInicio, dtFim);

            return ResponseEntity.ok().body(extrato);
        } catch (Exception e) {
            logger.error("\n::ContaCorrenteController:: extrato()\nErrorMessage: " + e.getMessage() +
                "\nErrorCause: " + e.getCause());
            return ResponseEntity.badRequest().body(null);
        }
    }



}
