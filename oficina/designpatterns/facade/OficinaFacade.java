package com.mycompany.oficina.designpatterns.facade;

import com.mycompany.oficina.model.Agendamento;
import com.mycompany.oficina.model.Cliente;
import com.mycompany.oficina.model.Item;
import com.mycompany.oficina.model.OrdemServico;
import com.mycompany.oficina.model.Servico;
import com.mycompany.oficina.model.StatusOS;
import com.mycompany.oficina.model.Veiculo;
import com.mycompany.oficina.persistence.AgendamentoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Fachada de alto nível para agendar serviços e gerar ordens de serviço.
 */
public class OficinaFacade {

    private final VerificadorDisponibilidadeService verificador = new VerificadorDisponibilidadeService();
    private final CalculadorOrcamentoService calculador      = new CalculadorOrcamentoService();
    private final GeradorOrdemDeServicoService gerador        = new GeradorOrdemDeServicoService();
    private final AgendamentoRepository agRepo               = new AgendamentoRepository();

    /**
     * Agrega o fluxo de criação de Agendamento, verificação, cálculo de orçamento e geração de OrdemServico.
     *
     * @param cliente           Cliente que solicita o serviço.
     * @param veiculo           Veículo associado.
     * @param dataHora          Data e hora agendada.
     * @param itensDesejados    Lista de itens (peças ou serviços) para inclusão.
     * @param descricaoProblema Descrição do problema relatado pelo cliente.
     * @param estadoInicial     Estado inicial do veículo na entrada.
     * @return OrdemServico     Ordem de serviço gerada e persistida.
     */
    public OrdemServico agendarServicoCompleto(Cliente cliente,
                                               Veiculo veiculo,
                                               LocalDateTime dataHora,
                                               List<Item> itensDesejados,
                                               String descricaoProblema,
                                               String estadoInicial) {
        // 1) Criar e salvar o Agendamento usando construtor vazio + setters
        Agendamento ag = new Agendamento();
        ag.setId(agRepo.listarTodos().size() + 1);
        ag.setDataHora(dataHora);
        ag.setStatus(StatusOS.RECEBIDO);
        ag.setCliente(cliente);
        ag.setVeiculo(veiculo);
        ag.setDescricaoProblema(descricaoProblema);
        ag.setEstadoInicial(estadoInicial);
        ag.setValorRetido(0.0);
        agRepo.add(ag);

        // 2) Verificar disponibilidade
        if (!verificador.verificarDisponibilidade(cliente, veiculo, dataHora)) {
            throw new IllegalStateException("Data e horário indisponíveis para o veículo especificado.");
        }

        // 3) Filtrar apenas os itens do tipo Servico para cálculo de orçamento
        List<Servico> servicos = itensDesejados.stream()
            .filter(i -> i instanceof Servico)
            .map(i -> (Servico) i)
            .collect(Collectors.toList());

        // 4) Calcular orçamento
        double orcamento = calculador.calcularOrcamento(servicos);

        // 5) Gerar e retornar a OrdemServico
        return gerador.gerarOrdem(ag, itensDesejados, dataHora);
    }

    /**
     * Calcula apenas o orçamento a partir da lista de itens tipo Servico.
     *
     * @param itensDesejados Lista de itens (peças ou serviços).
     * @return Valor total do orçamento.
     */
    public double calcularOrcamentoCompleto(List<Item> itensDesejados) {
        List<Servico> servicos = itensDesejados.stream()
            .filter(i -> i instanceof Servico)
            .map(i -> (Servico) i)
            .collect(Collectors.toList());

        return calculador.calcularOrcamento(servicos);
    }
}
