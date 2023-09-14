package com.accenture.academico.bank.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import com.accenture.academico.bank.model.Cliente;
import com.accenture.academico.bank.repository.ClienteRepository;

public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    public ClienteServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveOrUpdateCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("João");

        // Configuração do comportamento simulado do clienteRepository.save()
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Chama o método a ser testado
        Cliente result = clienteService.saveOrUpdateCliente(cliente);

        // Verifica se o resultado é igual ao cliente esperado
        assertEquals("João", result.getNome());
    }

    // Implemente mais testes para outros métodos do ClienteService
}
