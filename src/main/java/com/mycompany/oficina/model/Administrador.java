package com.mycompany.oficina.model;

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
        // TODO: delegar ao service/repository
    }

    /** Edita os dados de um cliente existente. */
    public void editarCliente(Cliente cliente) {
        // TODO: delegar ao service/repository
    }

    /** Remove um cliente pelo seu ID. */
    public void removerCliente(int clienteId) {
        // TODO: delegar ao service/repository
    }

    /** Registra uma nova despesa no sistema. */
    public void registrarDespesa(Despesa despesa) {
        // TODO: delegar ao service/repository
    }

    /**
     * Gera o balanço mensal para o mês/ano informados.
     * @return objeto BalancoMensal com receitas e despesas
     */
    public BalancoMensal gerarBalanco(int mes, int ano) {
        // TODO: delegar ao service
        return null;
    }
}
