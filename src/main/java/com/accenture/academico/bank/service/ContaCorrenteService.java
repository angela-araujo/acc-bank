package com.accenture.academico.bank.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.academico.bank.interfaces.IConta;
import com.accenture.academico.bank.model.Conta;
import com.accenture.academico.bank.model.ContaCorrente;
import com.accenture.academico.bank.model.Extrato;
import com.accenture.academico.bank.model.Operacao;
import com.accenture.academico.bank.repository.ContaCorrenteRepository;
import com.accenture.academico.bank.repository.ExtratoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ContaCorrenteService implements IConta {

    @Autowired
    ContaCorrenteRepository contaCorrenteRepository;

    @Autowired
    ExtratoRepository extratoRepository;

    public void creditar(Conta conta, BigDecimal valor, Operacao operacao, Conta origemDoCredito) throws Exception {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Valor deve ser maior que zero.");
        }

        conta.setSaldo(conta.getSaldo().add(valor));
        contaCorrenteRepository.save((ContaCorrente)conta);

        String descricaoDaOperacao = "";

        switch (operacao) {
            case CREDITO_TRANSFERENCIA:
                descricaoDaOperacao = "Transferência recebida de Ag " + origemDoCredito.getAgencia().getNumAgencia() + "/" + origemDoCredito.getNumero();
                break;
        
            case DEPOSITO:
                descricaoDaOperacao = "Crédito por depósito";
                break;

            default:
                descricaoDaOperacao = "";
                break;
        }        

        registrarExtrato(conta, operacao, valor, conta.getSaldo(), descricaoDaOperacao);
    }

    public void debitar(Conta conta, BigDecimal valor, Operacao operacao, Conta origemDoDebito) throws Exception {
        if (valor.compareTo(conta.getSaldo()) > 0) {
            throw new Exception("Saldo insuficiente!");
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Valor deve ser maior que zero.");
        }

        conta.setSaldo(conta.getSaldo().subtract(valor));
        contaCorrenteRepository.save((ContaCorrente) conta);

        String descricaoDaOperacao = "";

        switch (operacao) {
            case DEBITO_TRANSFERENCIA:
                descricaoDaOperacao = "Transferência feita para " + origemDoDebito.getAgencia().getNumAgencia() + "/" + origemDoDebito.getNumero();;
                break;

            case SAQUE:
                descricaoDaOperacao = "Débito por saque";
                break;

            default:
                descricaoDaOperacao = "";
                break;
        }

        registrarExtrato(conta, operacao, valor, conta.getSaldo(), descricaoDaOperacao);
    }

    public ContaCorrente getContaById(Long id) throws Exception {

        Optional<ContaCorrente> contaCorrente = contaCorrenteRepository.findById(id);

        if (!contaCorrenteRepository.findById(id).isPresent()) {
            throw new Exception("Conta não encontrada");
        } 
        return contaCorrente.get();
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
        ContaCorrente newConta = contaCorrenteRepository.save(contaCorrente);
        registrarExtrato(newConta, Operacao.ABERTURA_CONTA, BigDecimal.ZERO, BigDecimal.ZERO, "Abertura de conta");
        return newConta;
    }

    @Override    
    public BigDecimal sacar(Conta conta, BigDecimal valor) throws Exception {

        debitar(conta, valor, Operacao.SAQUE, null);

        return getContaById(conta.getId()).getSaldo();

    }

    @Override
    public void depositar(Conta conta, BigDecimal valor) throws Exception {
        creditar(conta, valor, Operacao.DEPOSITO, null);
    }

    @Override
    public void transferir(Conta contaOrigem, Conta contaDestino, BigDecimal valor) throws Exception {

        // Retira da conta origem
        debitar(contaOrigem, valor, Operacao.DEBITO_TRANSFERENCIA, contaDestino);
        
        // Deposita na conta destino
        creditar(contaDestino, valor, Operacao.CREDITO_TRANSFERENCIA, contaOrigem);

    }

    @Override
    public void registrarExtrato(Conta conta, Operacao operacao, BigDecimal valorDaOperacao,
            BigDecimal saldoAposOperacao, String descricao) {
                
        LocalDateTime dataMovimentacao = LocalDateTime.now();

        Extrato operacaoDoExtrato = new Extrato();
        operacaoDoExtrato.setConta((ContaCorrente) conta);
        operacaoDoExtrato.setOperacao(operacao);
        operacaoDoExtrato.setDataMovimento(dataMovimentacao);
        operacaoDoExtrato.setValor(valorDaOperacao);
        operacaoDoExtrato.setSaldoAtual(saldoAposOperacao);
        operacaoDoExtrato.setDescricao(descricao);

        System.out.println("\n\n::operacaoDoExtrato: " + operacaoDoExtrato);
        extratoRepository.save(operacaoDoExtrato);
    }

}
