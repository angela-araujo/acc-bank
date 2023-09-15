package com.accenture.academico.bank.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.academico.bank.interfaces.IConta;
import com.accenture.academico.bank.model.Conta;
import com.accenture.academico.bank.model.ContaCorrente;
import com.accenture.academico.bank.model.Operacao;
import com.accenture.academico.bank.repository.ContaCorrenteRepository;

import jakarta.transaction.Transactional;

@Service
public class ContaCorrenteService implements IConta {

    @Autowired
    ContaCorrenteRepository contaCorrenteRepository;

    public ContaCorrente getContaById(Long id) throws Exception {

        Optional<ContaCorrente> contaCorrente = contaCorrenteRepository.findById(id);

        if (contaCorrenteRepository.findById(id).isPresent()) {
            return contaCorrente.get();
        } else {
            throw new Exception("Conta não encontrada");
        }
    }

    public ContaCorrente save(ContaCorrente contaCorrente) throws Exception{
        
        // if (contaCorrente.getId() == null) {
        //     ContaCorrente contaRecuperada = contaCorrenteRepository.findByAgenciaAndNumero(
        //         contaCorrente.getAgencia().getId(),
        //         contaCorrente.getNumero());

        //     System.out.println("::: contaRecuperada: " + contaRecuperada);

        //     if (contaRecuperada != null) {
        //         throw new Exception("Conta " + contaCorrente.getNumero() + " já existe na agência " + 
        //         contaCorrente.getAgencia().getNumAgencia());
        //     }
        // }
        return contaCorrenteRepository.save(contaCorrente);
    }

    @Override
    public BigDecimal sacar(Conta conta, BigDecimal valor) throws Exception {

        if (valor.compareTo(conta.getSaldo()) > 0) {
            throw new Exception("Saldo insuficiente!");
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Valor deve ser maior que zero.");
        }

        conta.setSaldo(conta.getSaldo().subtract(valor));
        contaCorrenteRepository.save((ContaCorrente)conta);

        registrarExtrato(conta, Operacao.SAQUE, valor, null);

        return conta.getSaldo();
    }

    @Override
    public void depositar(Conta conta, BigDecimal valor) throws Exception {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Valor deve ser maior que zero.");
        }

        conta.setSaldo(conta.getSaldo().add(valor));
        contaCorrenteRepository.save((ContaCorrente)conta);

        registrarExtrato(conta, Operacao.DEPOSITO, valor, null);
    }

    @Override
    @Transactional
    public void transferir(Conta contaOrigem, Conta contaDestino, BigDecimal valor) throws Exception {
        if (valor.compareTo(contaOrigem.getSaldo()) > 0) {
            throw new Exception("Saldo insuficiente!");
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Valor deve ser maior que zero.");
        }

        // Saca da origem
        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));

        // Deposita no destino
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

        // Salva no banco as operações
        contaCorrenteRepository.save((ContaCorrente)contaOrigem);
        contaCorrenteRepository.save((ContaCorrente)contaDestino);

        registrarExtrato(contaOrigem, Operacao.TRANSFENCIA, valor, "Conta destino: " + contaDestino);
    }

    @Override
    public void registrarExtrato(Conta conta, Operacao operacao, BigDecimal valor, String descricao) {
        // throw new UnsupportedOperationException("Unimplemented method 'registrarExtrato'");
        System.out.println("Extrato: " + conta + " | Operacao: " + operacao + " | valor: " + valor + " | descricao: " + descricao);
    }

}
