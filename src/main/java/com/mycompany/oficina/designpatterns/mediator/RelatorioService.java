package com.mycompany.oficina.designpatterns.mediator;

import com.mycompany.oficina.model.OrdemServico;
import com.mycompany.oficina.model.RelatorioVendas;
import com.mycompany.oficina.persistence.RelatorioRepository;
import com.mycompany.oficina.persistence.OrdemServicoRepository;

import java.util.Date;
import java.util.List;

/**
 * Serviço de relatórios que atualiza dados de vendas reagindo a eventos do mediador.
 */
public class RelatorioService implements ColegaOficina {
    private OficinaMediator mediator;
    private final RelatorioRepository repo       = new RelatorioRepository();
    private final OrdemServicoRepository osRepo = new OrdemServicoRepository();

    @Override
    public void setMediator(OficinaMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void enviarEvento(TipoEvento evento, Object dados) {
        // RelatorioService não retransmite eventos
    }

    @Override
    public void receberEvento(TipoEvento evento, Object dados) {
        switch (evento) {
            case SERVICO_CONCLUIDO:
            case PAGAMENTO_REALIZADO:
                atualizarVendas();
                break;
            default:
                // outros eventos não tratados
                break;
        }
    }

    /**
     * Gera um novo RelatorioVendas e persiste no repositório.
     */
    private void atualizarVendas() {
        // 1) Coletar todas as ordens para o relatório
        List<OrdemServico> ordens = osRepo.listarTodas();

        // 2) Gerar identificador sequencial
        int nextId = repo.findAll().size()+ 1;
        // 3) Computar período como agora
        Date agora = new Date();

        // 4) Construir o relatório
        RelatorioVendas rel = new RelatorioVendas(
            nextId,
            agora,
            agora,
            ordens
        );

        // 5) Persistir
        repo.add(rel);
    }
}
