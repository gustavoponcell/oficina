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

    /**
     * Inclui um novo cliente no sistema usando a instância de {@link Sistema}.
     */
    public void incluirCliente(Sistema sistema, Cliente cliente) {
        if (sistema == null || cliente == null) {
            System.out.println("Dados inválidos para inclusão de cliente.");
            return;
        }
        sistema.addCliente(cliente);
        System.out.println("Cliente incluído: " + cliente.getNome());
    }

    /**
     * Edita os dados de um cliente existente.
     */
    public void editarCliente(Sistema sistema, Cliente cliente) {
        if (sistema == null || cliente == null) {
            System.out.println("Dados inválidos para edição de cliente.");
            return;
        }
        sistema.updateCliente(cliente);
        System.out.println("Cliente atualizado: " + cliente.getNome());
    }

    /**
     * Remove um cliente pelo seu ID.
     */
    public void removerCliente(Sistema sistema, int clienteId) {
        if (sistema == null) {
            System.out.println("Sistema não informado para remoção de cliente.");
            return;
        }
        boolean ok = sistema.removeCliente(clienteId);
        if (ok) {
            System.out.println("Cliente removido: ID " + clienteId);
        } else {
            System.out.println("Cliente não encontrado: ID " + clienteId);
        }
    }

    /**
     * Registra uma nova despesa no sistema.
     */
    public void registrarDespesa(Sistema sistema, Despesa despesa) {
        if (sistema == null || despesa == null) {
            System.out.println("Dados inválidos para registro de despesa.");
            return;
        }
        sistema.registrarDespesa(despesa);
        System.out.println("Despesa registrada: " + despesa.getDescricao());
    }

    /**
     * Gera o balanço mensal para o mês/ano informados.
     * @return objeto BalancoMensal com receitas e despesas
     */
    public BalancoMensal gerarBalanco(Sistema sistema, int mes, int ano) {
        if (sistema == null) {
            System.out.println("Sistema não informado para gerar balanço.");
            return null;
        }
        BalancoMensal balanco = sistema.gerarBalancoMes(mes, ano);
        if (balanco != null) {
            System.out.println("Balanço gerado para " + mes + "/" + ano);
        }
        return balanco;
    }
}
