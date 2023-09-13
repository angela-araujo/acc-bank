package com.accenture.academico.bank.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.accenture.academico.bank.model.Cliente;
import com.accenture.academico.bank.service.ClienteService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    public void testGetById() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("João");

        when(clienteService.getClienteById(1L)).thenReturn(cliente);

        mockMvc.perform(get("/api/v1/cliente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    // Implemente mais testes para outros métodos do ClienteController
}
