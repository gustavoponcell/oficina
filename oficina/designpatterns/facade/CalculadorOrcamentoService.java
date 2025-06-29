package com.mycompany.oficina.designpatterns.facade;

import com.mycompany.oficina.model.Servico;
import java.util.List;

public class CalculadorOrcamentoService {
    public double calcularOrcamento(List<Servico> servicos) {
        // Ajuste Servico::getValor conforme o nome real no seu código
        return servicos.stream()
                       .mapToDouble(Servico::getValor)
                       .sum();
    }
}
