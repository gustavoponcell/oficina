package com.mycompany.oficina.designpatterns.mediator;

import com.mycompany.oficina.model.Produto;
import com.mycompany.oficina.model.Estoque;

import java.util.List;
import java.util.Map;

/**
 * Serviço de controle de estoque que ajusta quantidades de peças,
 * reagindo a eventos do mediador.
 */
public class EstoqueService implements ColegaOficina {
    private OficinaMediator mediator;
    private final Estoque repo = new Estoque();

    @Override
    public void setMediator(OficinaMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void enviarEvento(TipoEvento evento, Object dados) {
        // Estoque não retransmite eventos
    }

    @Override
    public void receberEvento(TipoEvento evento, Object dados) {
        switch (evento) {
            case SERVICO_CONCLUIDO:
                ajustarEstoqueParaServico(dados);
                break;

            case PECA_RESERVADA:
                reservarPeca(dados);
                break;

            default:
                // não trata outros eventos
                break;
        }
    }

    @SuppressWarnings("unchecked")
    private void ajustarEstoqueParaServico(Object dados) {
        // 'dados' deve ter detalhes do agendamento ou ordem de serviço
        // aqui assumimos que um Map<Produto, Integer> é passado como payload
        Map<Produto, Integer> itens = (Map<Produto, Integer>) dados;
        for (Map.Entry<Produto, Integer> entry : itens.entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            repo.removerEstoque(produto, quantidade);
        }
    }

    @SuppressWarnings("unchecked")
    private void reservarPeca(Object dados) {
        // Similar ao ajuste, mas sem retirada imediata, apenas reserva lógica
        Map<Produto, Integer> itens = (Map<Produto, Integer>) dados;
        for (Map.Entry<Produto, Integer> entry : itens.entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            repo.removerEstoque(produto, quantidade);  // ou marcar reserva
        }
    }
}
