package com.mycompany.oficina.model;

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

    /** Registra um agendamento de serviço. */
    public void registrarAgendamento(Agendamento agendamento) {
        // TODO: delegar ao service/repository
    }

    /** Cancela um agendamento já existente. */
    public void cancelarAgendamento(Agendamento agendamento) {
        // TODO: delegar ao service/repository
    }
}
