package com.mycompany.oficina.designpatterns.mediator;

/**
 * Interface que define o contrato para módulos ("colegas") que se
 * comunicam através do OficinaMediator.
 */
public interface ColegaOficina {
    /**
     * Define o mediador que este colega irá usar para enviar e receber eventos.
     *
     * @param mediator instância central de comunicação
     */
    void setMediator(OficinaMediator mediator);

    /**
     * Envia um evento para o mediador, que irá roteá-lo aos módulos interessados.
     *
     * @param evento tipo de evento disparado
     * @param dados  objeto de dados associado ao evento (pode ser null)
     */
    void enviarEvento(TipoEvento evento, Object dados);

    /**
     * Recebe notificação de um evento roteado pelo mediador.
     *
     * @param evento tipo de evento recebido
     * @param dados  objeto de dados associado ao evento (pode ser null)
     */
    void receberEvento(TipoEvento evento, Object dados);
}
