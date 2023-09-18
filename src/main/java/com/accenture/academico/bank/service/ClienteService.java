package com.accenture.academico.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.academico.bank.model.Cliente;
import com.accenture.academico.bank.repository.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {

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
    
    public Cliente getClienteById(long id) throws Exception {
        Cliente cliente = clienteRepository.findById(id).get();

        if (!clienteRepository.findById(id).isPresent()) {
            throw new Exception("Cliente não encontrado");
        }
        
        return cliente;
    }

    public Cliente getClienteByCPF(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }
}
