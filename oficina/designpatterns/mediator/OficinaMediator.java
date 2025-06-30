package com.mycompany.oficina.designpatterns.mediator;

/**
 * Central de comunicação desacoplada entre os módulos ("colegas") da Oficina.
 */
public class OficinaMediator {

    private final ColegaOficina agendamento;
    private final ColegaOficina financeiro;
    private final ColegaOficina estoque;
    private final ColegaOficina relatorio;

    /**
     * Constrói o mediador, registrando cada colega e configurando a comunicação bidirecional.
     *
     * @param agendamento referência ao serviço de agendamento
     * @param financeiro  referência ao serviço financeiro
     * @param estoque     referência ao serviço de controle de estoque
     * @param relatorio   referência ao serviço de relatórios
     */
    public OficinaMediator(ColegaOficina agendamento,
                           ColegaOficina financeiro,
                           ColegaOficina estoque,
                           ColegaOficina relatorio) {
        this.agendamento = agendamento;
        this.financeiro  = financeiro;
        this.estoque     = estoque;
        this.relatorio   = relatorio;

        // Configura cada colega para usar este mediador
        this.agendamento.setMediator(this);
        this.financeiro .setMediator(this);
        this.estoque    .setMediator(this);
        this.relatorio  .setMediator(this);
    }

    /**
     * Recebe um evento de qualquer colega e o encaminha aos colegas interessados.
     *
     * @param evento tipo de evento a ser roteado
     * @param dados  dados associados ao evento (por exemplo, objeto Agendamento ou OrdemServico)
     */
    public void enviarEvento(TipoEvento evento, Object dados) {
        switch (evento) {
            case AGENDAMENTO_CRIADO:
                // Notifica apenas o financeiro para lançamento inicial
                financeiro.receberEvento(evento, dados);
                break;

            case SERVICO_CONCLUIDO:
                // Quando um serviço é concluído:
                // 1) Processar pagamento no financeiro
                // 2) Atualizar estoque (peças usadas)
                // 3) Atualizar relatórios
                financeiro.receberEvento(evento, dados);
                estoque   .receberEvento(evento, dados);
                relatorio .receberEvento(evento, dados);
                break;

            case PECA_RESERVADA:
                // Quando uma peça é reservada para um serviço:
                // 1) Registrar custo financeiro
                // 2) Atualizar relatório de custos
                financeiro.receberEvento(evento, dados);
                relatorio .receberEvento(evento, dados);
                break;

            case PAGAMENTO_REALIZADO:
                // Após confirmação de pagamento, atualizar relatório financeiro
                relatorio.receberEvento(evento, dados);
                break;

            // outros eventos específicos podem ser tratados aqui...
        }
    }
}
