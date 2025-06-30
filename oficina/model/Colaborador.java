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

    /** Registra um agendamento de serviço. */
    public void registrarAgendamento(Agendamento agendamento) {
        Sistema sistema = new Sistema();
        sistema.registrarAgendamento(agendamento);
    }

    /** Cancela um agendamento já existente. */
    public void cancelarAgendamento(Agendamento agendamento) {
        Sistema sistema = new Sistema();
        sistema.cancelarAgendamento(agendamento.getId());
    }
}
