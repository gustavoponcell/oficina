// src/main/java/com/mycompany/oficina/designpatterns/mediator/TipoEvento.java
package com.mycompany.oficina.designpatterns.mediator;

/**
 * Tipos de evento suportados pelo Mediator da Oficina.
 */
public enum TipoEvento {
    AGENDAMENTO_CRIADO,
    SERVICO_CONCLUIDO,
    PECA_RESERVADA,
    PAGAMENTO_REALIZADO
    // adicione outros eventos conforme necessário
}
