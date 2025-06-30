package com.mycompany.oficina.model;

import java.util.Comparator;

/**
 * Comparator para Agendamento:
 * 1) ordena por data/hora do agendamento
 * 2) em caso de empate, ordena por ID do agendamento
 */
public class AgendamentoComparator implements Comparator<Agendamento> {

    @Override
    public int compare(Agendamento a1, Agendamento a2) {
        // 1) Primeiro compara data/hora
        int cmp = a1.getDataHora().compareTo(a2.getDataHora());
        if (cmp != 0) {
            return cmp;
        }
        // 2) Se for mesma data/hora, compara pelo ID (critério determinístico)
        return Integer.compare(a1.getId(), a2.getId());
    }
}
