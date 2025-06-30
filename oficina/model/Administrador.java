package com.mycompany.oficina.model;

import com.mycompany.oficina.service.Sistema;

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
        Sistema sistema = new Sistema();
        sistema.addCliente(cliente);
    }

    /** Edita os dados de um cliente existente. */
    public void editarCliente(Cliente cliente) {
        Sistema sistema = new Sistema();
        sistema.updateCliente(cliente);
    }

    /** Remove um cliente pelo seu ID. */
    public void removerCliente(int clienteId) {
        Sistema sistema = new Sistema();
        sistema.removeCliente(clienteId);
    }

    /** Registra uma nova despesa no sistema. */
    public void registrarDespesa(Despesa despesa) {
        Sistema sistema = new Sistema();
        sistema.registrarDespesa(despesa);
    }

    /**
     * Gera o balanço mensal para o mês/ano informados.
     * @return objeto BalancoMensal com receitas e despesas
     */
    public BalancoMensal gerarBalanco(int mes, int ano) {
        Sistema sistema = new Sistema();
        return sistema.gerarBalancoMes(mes, ano);
    }
}
