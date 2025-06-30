package com.mycompany.oficina.model;

/**
 * Representa os possíveis estados de um serviço/agendamento no sistema.
 */
public enum StatusOS {
    /** Foi recebido mas ainda não iniciado. */
    RECEBIDO,

    /** Em manutenção/executando serviço. */
    EM_MANUTENCAO,

    /** Pronto para ser entregue ao cliente. */
    PRONTO_ENTREGA,

    /** Serviço entregue e finalizado. */
    ENTREGUE
}
