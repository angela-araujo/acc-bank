package com.accenture.academico.bank.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.academico.bank.BankApplication;
import com.accenture.academico.bank.dto.ClienteDTO;
import com.accenture.academico.bank.model.Cliente;
import com.accenture.academico.bank.model.Endereco;
import com.accenture.academico.bank.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@Tag(name = "Cliente", description = "Operações relacionadas a clientes")
@RestController
@RequestMapping("/api/v1/cliente")
public class ClienteController {

    private static Logger logger = LoggerFactory.getLogger(BankApplication.class);

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private LocalValidatorFactoryBean validatorFactoryBean;

    @Operation(summary = "Obtém a lista de todos os cliente")
    @GetMapping()
    public List<Cliente> getAll() {
        return clienteService.getAllCliente();
    }

    @Operation(summary = "Obtém um cliente por ID")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class)))
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id) {
        try {
            Cliente cliente = clienteService.getClienteById(id);
            return ResponseEntity.ok().body(cliente);
        } catch (Exception e) {
            logger.error("::ClienteController:: getById()\nErrorMessage: \n" + e.getMessage() +
                    "\nErrorCause: " + e.getCause());
            return ResponseEntity.badRequest().body("Cliente não encontrado");
        }
    }

    @Operation(summary = "Obtém a lista de todos pelo nome")
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

    @Operation(summary = "Obtém um cliente por CPF")
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

    @Operation(summary = "Cadastra um cliente")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody ClienteDTO clienteDTO) {
        try {
            Endereco endereco = new Endereco(
                    clienteDTO.getLogradouro(),
                    clienteDTO.getNumero(),
                    clienteDTO.getComplemento(),
                    clienteDTO.getCep(),
                    clienteDTO.getBairro(),
                    clienteDTO.getCidade(),
                    clienteDTO.getEstado());

            Cliente cliente = new Cliente(
                    clienteDTO.getNome(),
                    clienteDTO.getCpf(),
                    clienteDTO.getTelefone(),
                    clienteDTO.getEmail(),
                    clienteDTO.getDataNascimento(),
                    endereco);

            Cliente newCliente = clienteService.saveOrUpdateCliente(cliente);

            return ResponseEntity.status(HttpStatus.CREATED).body(newCliente);

        } catch (ConstraintViolationException e) {
            logger.error("::ClienteController:: save()\nErrorMessage: \n" + e.getMessage() +
                    "\nErrorCause: " + e.getCause());
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

            String msgErro = "";
            for (ConstraintViolation<?> violation : violations) {
                String propertyPath = violation.getPropertyPath().toString();
                String message = violation.getMessage();

                if (propertyPath.equals("cpf")) {
                    msgErro = msgErro + "CPF inválido.\n";
                } else if (propertyPath.equals("email")) {
                    msgErro = msgErro + "E-mail inválido\n";
                } else {
                    msgErro = msgErro + "Erro de validação em " + propertyPath + ": " + message + "\n";
                }
            }
            return ResponseEntity.badRequest().body(msgErro);

        } catch (DataIntegrityViolationException e) {
            logger.error("::ClienteController:: save()\nErrorMessage: \n" + e.getMessage() +
                    "\nErrorCause: " + e.getCause());
            return ResponseEntity.badRequest().body("CPF deve ser único.");
        } catch (Exception e) {
            logger.error("::ClienteController:: save()\nErrorMessage: \n" + e.getMessage() +
                    "\nErrorCause: " + e.getCause());
            return ResponseEntity.badRequest().body(":: Erro: " + e.getMessage() + " \n:: Causa: " + e.getCause());
        }
    }

    @Operation(summary = "Apaga um cliente pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.getClienteById(id);
            clienteService.deleteCliente(cliente.getId());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente excluído com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(":: Erro: " + e.getMessage());
        }
    }

    @Operation(summary = "Atualiza dados de cliente")
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@RequestBody ClienteDTO clienteDTO, @PathVariable(value = "id") Long id) {
        try {

            Cliente cliente = clienteService.getClienteById(id);

            if ((clienteDTO.getLogradouro() != null) || (clienteDTO.getNumero() != null) ||
                    (clienteDTO.getComplemento() != null) || (clienteDTO.getCep() != null) ||
                    (clienteDTO.getBairro() != null) ||
                    (clienteDTO.getCidade() != null) || (clienteDTO.getEstado() != null)) {

                Endereco endereco = cliente.getEndereco();

                if (clienteDTO.getLogradouro() != null) {
                    endereco.setLogradouro(clienteDTO.getLogradouro());
                }

                if (clienteDTO.getNumero() != null) {
                    endereco.setNumero(clienteDTO.getNumero());
                }

                if (clienteDTO.getComplemento() != null) {
                    endereco.setComplemento(clienteDTO.getComplemento());
                }

                if (clienteDTO.getCep() != null) {
                    endereco.setCep(clienteDTO.getCep());
                }

                if (clienteDTO.getBairro() != null) {
                    endereco.setBairro(clienteDTO.getBairro());
                }

                if (clienteDTO.getCidade() != null) {
                    endereco.setCidade(clienteDTO.getCidade());
                }

                if (clienteDTO.getEstado() != null) {
                    endereco.setEstado(clienteDTO.getEstado());
                }

                if (clienteDTO.getNome() != null) {
                    cliente.setNome(clienteDTO.getNome());
                }

                if (clienteDTO.getCpf() != null) {
                    cliente.setCpf(clienteDTO.getCpf());
                }

                if (clienteDTO.getTelefone() != null) {
                    cliente.setTelefone(clienteDTO.getTelefone());
                }

                if (clienteDTO.getEmail() != null) {
                    cliente.setEmail(clienteDTO.getEmail());
                }

                if (clienteDTO.getDataNascimento() != null) {
                    cliente.setDataNascimento(clienteDTO.getDataNascimento());
                }

                // Validação do Endereço
                Set<ConstraintViolation<Endereco>> enderecoViolations = validatorFactoryBean.validate(endereco);

                if (!enderecoViolations.isEmpty()) {
                    // Trate os erros de validação do Endereço aqui
                    String errorMessage = "Erros de validação do Endereço:";
                    for (ConstraintViolation<Endereco> violation : enderecoViolations) {
                        errorMessage = errorMessage + "\n" + violation.getMessage();
                    }
                    throw new Exception(errorMessage);
                }

                cliente.setEndereco(endereco);
            }

            //////////

            if (clienteDTO.getNome() != null) {
                cliente.setNome(clienteDTO.getNome());
            }

            if (clienteDTO.getCpf() != null) {
                cliente.setCpf(clienteDTO.getCpf());
            }

            if (clienteDTO.getTelefone() != null) {
                cliente.setTelefone(clienteDTO.getTelefone());
            }

            if (clienteDTO.getEmail() != null) {
                cliente.setEmail(clienteDTO.getEmail());
            }

            if (clienteDTO.getDataNascimento() != null) {
                cliente.setDataNascimento(clienteDTO.getDataNascimento());
            }

            System.out.println("\n\nclienteUpdate: \n" + cliente);
            Cliente clienteUpdated = clienteService.saveOrUpdateCliente(cliente);

            return ResponseEntity.status(HttpStatus.OK).body(clienteUpdated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(":: Erro: " + e.getMessage());
        }
    }
}
