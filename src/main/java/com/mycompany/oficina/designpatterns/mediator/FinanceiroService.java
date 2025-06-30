// src/main/java/com/mycompany/oficina/designpatterns/mediator/FinanceiroService.java
package com.mycompany.oficina.designpatterns.mediator;

import com.mycompany.oficina.model.Despesa;
import com.mycompany.oficina.persistence.DespesaRepository;

import java.util.Date;

/**
 * Serviço financeiro que registra custos e pagamentos,
 * reagindo a eventos do mediador.
 */
public class FinanceiroService implements ColegaOficina {
    private OficinaMediator mediator;
    private final DespesaRepository repo = new DespesaRepository();

    @Override
    public void setMediator(OficinaMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void enviarEvento(TipoEvento evento, Object dados) {
        // Financeiro não retransmite eventos via mediador
    }

    @Override
    public void receberEvento(TipoEvento evento, Object dados) {
        switch (evento) {
            case AGENDAMENTO_CRIADO:
                // Nenhuma ação financeira imediata
                break;

            case SERVICO_CONCLUIDO:
                // Ao concluir serviço, criar uma despesa (pagamento a registrar)
                registrarPagamento(dados);
                mediator.enviarEvento(TipoEvento.PAGAMENTO_REALIZADO, dados);
                break;

            case PECA_RESERVADA:
                // Registrar custo de peça no estoque
                registrarCustoPeca(dados);
                break;

            default:
                // outros eventos não tratados
                break;
        }
    }

    private void registrarPagamento(Object dados) {
        // 'dados' é o Agendamento concluído
        var ag = (com.mycompany.oficina.model.Agendamento) dados;
        Despesa d = new Despesa();
        d.setId(repo.findAll().size() + 1);
        d.setDescricao("Pagamento do serviço agendado ID " + ag.getId());
        // sem valor exato aqui, definir 0; você pode calcular a partir da OrdemServico
        d.setValor(0.0);
        d.setData(new Date());
        repo.add(d);
    }

    private void registrarCustoPeca(Object dados) {
        // 'dados' é o Agendamento ou detalhe de peça
        var ag = (com.mycompany.oficina.model.Agendamento) dados;
        Despesa d = new Despesa();
        d.setId(repo.findAll().size() + 1);
        d.setDescricao("Custo de peça para agendamento ID " + ag.getId());
        d.setValor(0.0);
        d.setData(new Date());
        repo.add(d);
    }
}
