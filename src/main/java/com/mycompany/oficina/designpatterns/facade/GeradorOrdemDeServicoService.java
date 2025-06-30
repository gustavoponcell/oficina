package com.mycompany.oficina.designpatterns.facade;

import com.mycompany.oficina.model.Agendamento;
import com.mycompany.oficina.model.Item;
import com.mycompany.oficina.model.OrdemServico;
import com.mycompany.oficina.persistence.OrdemServicoRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Facade para criação e persistência de ordens de serviço.
 */
public class GeradorOrdemDeServicoService {
    private final OrdemServicoRepository repo = new OrdemServicoRepository();

    /**
     * Gera e persiste uma ordem de serviço a partir de um agendamento e lista de itens.
     * Também calcula o valor (criando a fatura), atualiza o orçamento e a data de abertura,
     * e emite a nota fiscal.
     *
     * @param agendamento   o agendamento base
     * @param itens         peças/serviços inclusos
     * @param dataAbertura  data e hora de abertura da OS
     * @return a OS recém-criada
     */
    public OrdemServico gerarOrdem(Agendamento agendamento,
                                   List<Item> itens,
                                   LocalDateTime dataAbertura) {
        // 1) define ID sequencial
        int id = repo.listarTodas().size() + 1;

        // 2) cria a OS usando o construtor atual
        OrdemServico os = new OrdemServico(id, agendamento, itens);

        // 3) calcula o valor total (gera a fatura internamente)
        double total = os.calcularValor();

        // 4) atualiza orçamento e data de abertura
        os.setOrcamento(total);
        os.setDataAbertura(dataAbertura);

        // 5) persiste a ordem
        repo.salvar(os);

        // 6) emite a nota fiscal
        os.gerarNotaFiscal();

        return os;
    }
}
