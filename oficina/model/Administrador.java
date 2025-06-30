package com.mycompany.oficina.model;

import com.mycompany.oficina.persistence.ClienteRepository;
import com.mycompany.oficina.persistence.DespesaRepository;
import com.mycompany.oficina.persistence.FaturaRepository;

/**
 * Usuário com privilégios administrativos:
 * pode gerenciar clientes e despesas, e gerar balanços.
 */
public class Administrador extends Usuario {

    public Administrador() {
        super();
    }

    public Administrador(String username, String password) {
        super(username, password);
    }

    /** Inclui um novo cliente no sistema. */
    public void incluirCliente(Cliente cliente) {
        ClienteRepository repo = new ClienteRepository();
        repo.add(cliente);
    }

    /** Edita os dados de um cliente existente. */
    public void editarCliente(Cliente cliente) {
        ClienteRepository repo = new ClienteRepository();
        repo.update(cliente);
    }

    /** Remove um cliente pelo seu ID. */
    public void removerCliente(int clienteId) {
        ClienteRepository repo = new ClienteRepository();
        repo.remove(clienteId);
    }

    /** Registra uma nova despesa no sistema. */
    public void registrarDespesa(Despesa despesa) {
        DespesaRepository repo = new DespesaRepository();
        repo.add(despesa);
    }

    /**
     * Gera o balanço mensal para o mês/ano informados.
     * @return objeto BalancoMensal com receitas e despesas
     */
    public BalancoMensal gerarBalanco(int mes, int ano) {
        FaturaRepository fRepo = new FaturaRepository();
        DespesaRepository dRepo = new DespesaRepository();

        double receitas = fRepo.findAll().stream()
                .filter(f -> {
                    java.util.Calendar c = java.util.Calendar.getInstance();
                    c.setTime(f.getData());
                    return c.get(java.util.Calendar.MONTH) + 1 == mes &&
                           c.get(java.util.Calendar.YEAR) == ano;
                })
                .mapToDouble(Fatura::getValorTotal)
                .sum();

        double despesas = dRepo.findAll().stream()
                .filter(d -> {
                    java.util.Calendar c = java.util.Calendar.getInstance();
                    c.setTime(d.getData());
                    return c.get(java.util.Calendar.MONTH) + 1 == mes &&
                           c.get(java.util.Calendar.YEAR) == ano;
                })
                .mapToDouble(Despesa::getValor)
                .sum();

        BalancoMensal b = new BalancoMensal();
        b.setMes(mes);
        b.setAno(ano);
        b.setReceitas(receitas);
        b.setDespesas(despesas);
        return b;
    }
}
