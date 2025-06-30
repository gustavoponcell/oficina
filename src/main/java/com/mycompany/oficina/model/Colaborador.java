package com.mycompany.oficina.model;

import com.mycompany.oficina.persistence.ClienteRepository;
import com.mycompany.oficina.persistence.AgendamentoRepository;

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

    /** Inclui um novo cliente no sistema. */
    public void incluirCliente(Cliente cliente) {
        ClienteRepository repo = new ClienteRepository();
        repo.add(cliente);
    }

    /** Edita os dados de um cliente existente. */
    public void editarCliente(Cliente cliente) {
        ClienteRepository repo = new ClienteRepository();
        boolean atualizado = repo.update(cliente);
        if (!atualizado) {
            throw new RuntimeException(
                "Falha ao atualizar cliente com ID " + cliente.getId());
        }
    }

    /** Remove um cliente pelo seu ID. */
    public void removerCliente(int clienteId) {
        ClienteRepository repo = new ClienteRepository();
        boolean removido = repo.remove(clienteId);
        if (!removido) {
            throw new RuntimeException(
                "Falha ao remover cliente com ID " + clienteId);
        }
    }

    /** Registra um agendamento de serviço. */
    public void registrarAgendamento(Agendamento agendamento) {
        AgendamentoRepository repo = new AgendamentoRepository();
        repo.add(agendamento);
    }

    /** Cancela um agendamento já existente. */
    public void cancelarAgendamento(Agendamento agendamento) {
        AgendamentoRepository repo = new AgendamentoRepository();
        boolean cancelado = repo.remove(agendamento.getId());
        if (!cancelado) {
            throw new RuntimeException(
                "Falha ao cancelar agendamento com ID " + agendamento.getId());
        }
    }
}
