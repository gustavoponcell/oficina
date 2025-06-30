package com.mycompany.oficina.model;

import com.mycompany.oficina.service.Sistema;

/**
 * Usuário operacional:
 * pode gerenciar clientes básicos e agendamentos.
 */
public class Colaborador extends Usuario {

    public Colaborador() {
        super();
    }

    public Colaborador(String username, String password) {
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
     * Registra um agendamento de serviço.
     */
    public void registrarAgendamento(Sistema sistema, Agendamento agendamento) {
        if (sistema == null || agendamento == null) {
            System.out.println("Dados inválidos para registro de agendamento.");
            return;
        }
        sistema.registrarAgendamento(agendamento);
        System.out.println("Agendamento registrado para cliente "
                + agendamento.getCliente().getNome());
    }

    /**
     * Cancela um agendamento já existente.
     */
    public void cancelarAgendamento(Sistema sistema, Agendamento agendamento) {
        if (sistema == null || agendamento == null) {
            System.out.println("Dados inválidos para cancelamento de agendamento.");
            return;
        }
        sistema.cancelarAgendamento(agendamento.getId());
        System.out.println("Agendamento cancelado: ID " + agendamento.getId());
    }
}
