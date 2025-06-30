package com.mycompany.oficina.designpatterns.mediator;

import com.mycompany.oficina.model.Agendamento;
import com.mycompany.oficina.model.Cliente;
import com.mycompany.oficina.model.Veiculo;
import com.mycompany.oficina.model.StatusOS;
import com.mycompany.oficina.persistence.AgendamentoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Serviço de agendamento que cria e conclui agendamentos,
 * comunicando-se com outros módulos via OficinaMediator.
 */
public class AgendamentoService implements ColegaOficina {
    private OficinaMediator mediator;
    private final AgendamentoRepository repo = new AgendamentoRepository();

    @Override
    public void setMediator(OficinaMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void enviarEvento(TipoEvento evento, Object dados) {
        // Os módulos não reenviam seus próprios eventos aqui
    }

    @Override
    public void receberEvento(TipoEvento evento, Object dados) {
        // AgendamentoService não reage a eventos de outros colegas
    }

    /**
     * Cria um novo Agendamento, persiste e notifica o Mediator.
     *
     * @param cliente           Cliente que solicitou o serviço
     * @param veiculo           Veículo do cliente
     * @param dataHora          Data e hora agendada
     * @param descricaoProblema Descrição do problema
     * @param estadoInicial     Estado inicial do veículo
     * @return o Agendamento criado
     */
    public Agendamento criarAgendamento(Cliente cliente,
                                       Veiculo veiculo,
                                       LocalDateTime dataHora,
                                       String descricaoProblema,
                                       String estadoInicial) {
        int nextId = repo.listarTodos().size() + 1;
        Agendamento ag = new Agendamento();
        ag.setId(nextId);
        ag.setDataHora(dataHora);
        ag.setStatus(StatusOS.RECEBIDO);
        ag.setCliente(cliente);
        ag.setVeiculo(veiculo);
        ag.setDescricaoProblema(descricaoProblema);
        ag.setEstadoInicial(estadoInicial);
        ag.setValorRetido(0.0);

        repo.add(ag);
        mediator.enviarEvento(TipoEvento.AGENDAMENTO_CRIADO, ag);
        return ag;
    }

    /**
     * Conclui um Agendamento existente, atualiza status e notifica o Mediator.
     *
     * @param agendamentoId ID do agendamento a concluir
     */
    public void concluirServico(int agendamentoId) {
        Optional<Agendamento> opt = repo.listarTodos().stream()
            .filter(a -> a.getId() == agendamentoId)
            .findFirst();

        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Agendamento não encontrado: " + agendamentoId);
        }

        Agendamento ag = opt.get();
        ag.setStatus(StatusOS.ENTREGUE);
        // não há update explícito: o objeto em memória já foi alterado
        mediator.enviarEvento(TipoEvento.SERVICO_CONCLUIDO, ag);
    }
}
