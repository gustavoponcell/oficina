package com.mycompany.oficina.model;

import com.mycompany.oficina.persistence.ClienteRepository;
import com.mycompany.oficina.persistence.DespesaRepository;
import com.mycompany.oficina.persistence.FaturaRepository;
import com.mycompany.oficina.persistence.RelatorioRepository;

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
        RelatorioRepository rRepo = new RelatorioRepository();

        double receitas = fRepo.findAll().stream()
            .filter(f -> {
                java.time.LocalDate d = com.mycompany.oficina.util.DateUtil.toLocalDate(f.getData());
                return d.getMonthValue() == mes && d.getYear() == ano;
            })
            .mapToDouble(Fatura::getValorTotal)
            .sum();

        double despesas = dRepo.findAll().stream()
            .filter(d -> {
                java.time.LocalDate ld = com.mycompany.oficina.util.DateUtil.toLocalDate(d.getData());
                return ld.getMonthValue() == mes && ld.getYear() == ano;
            })
            .mapToDouble(Despesa::getValor)
            .sum();

        BalancoMensal balanco = new BalancoMensal();
        balanco.setId(rRepo.findAll().size() + 1);
        balanco.setMes(mes);
        balanco.setAno(ano);
        balanco.setReceitas(receitas);
        balanco.setDespesas(despesas);

        rRepo.add(balanco);
        return balanco;
    }
}
