package com.accenture.academico.bank.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.academico.bank.BankApplication;
import com.accenture.academico.bank.model.Cliente;
import com.accenture.academico.bank.repository.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {

    private static Logger logger = LoggerFactory.getLogger(BankApplication.class);

    @Autowired
    ClienteRepository clienteRepository;

    public List<Cliente> getAllCliente() {
        return clienteRepository.findAll();
    }

    @Transactional
    public Cliente saveOrUpdateCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    public void deleteCliente(long id) {
        clienteRepository.deleteById(id);
    }
    
    public List<Cliente> getClienteByName(String nome) {
        return clienteRepository.findByPartialName(nome);
    }
    
    public Cliente getClienteById(long id) {
        Cliente cliente = clienteRepository.findById(id).get();
        logger.info(":: [ClienteService.getClienteById] - cliente: " + cliente);

        if (clienteRepository.findById(id).isPresent()) {
            logger.info(":: [ClienteService.getClienteById]" + cliente);
            return cliente;
        }

        return null;
    }

    public Cliente getClienteByCPF(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }
}
