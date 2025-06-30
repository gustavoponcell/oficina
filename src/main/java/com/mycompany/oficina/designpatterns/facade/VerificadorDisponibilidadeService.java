package com.mycompany.oficina.designpatterns.facade;

import com.mycompany.oficina.model.Cliente;
import com.mycompany.oficina.model.Veiculo;
import com.mycompany.oficina.persistence.AgendamentoRepository;
import com.mycompany.oficina.model.Agendamento;

import java.time.LocalDateTime;
import java.util.List;

public class VerificadorDisponibilidadeService {
    private AgendamentoRepository agRepo = new AgendamentoRepository();

    public boolean verificarDisponibilidade(Cliente cliente, Veiculo veiculo, LocalDateTime dataHora) {
        List<Agendamento> ags = agRepo.listarTodos();
        for (Agendamento ag : ags) {
            if (ag.getVeiculo().equals(veiculo) && ag.getDataHora().equals(dataHora)) {
                return false;
            }
        }
        return true;
    }
}
