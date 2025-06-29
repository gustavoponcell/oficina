package com.mycompany.oficina.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Relatório de vendas em um período, incluindo todas as ordens de serviço.
 */
public class RelatorioVendas extends Relatorio {

    private List<OrdemServico> ordens;

    /** Construtor vazio para frameworks de desserialização. */
    public RelatorioVendas() {
        super();
    }

    /**
     * Construtor completo.
     * @param id         identificador do relatório
     * @param dataInicio início do período
     * @param dataFim    fim do período
     * @param ordens     lista de ordens de serviço
     */
    public RelatorioVendas(int id, Date dataInicio, Date dataFim, List<OrdemServico> ordens) {
        super(id, dataInicio, dataFim);
        this.ordens = ordens;
    }

    public List<OrdemServico> getOrdens() {
        return ordens;
    }

    public void setOrdens(List<OrdemServico> ordens) {
        this.ordens = ordens;
    }

    /**
     * Gera o relatório de vendas, somando valores e listando ordens.
     */
    @Override
    public void gerar() {
        double totalVendas = ordens.stream()
                .mapToDouble(OrdemServico::calcularValor)
                .sum();

        System.out.println("Relatório de Vendas - Período: " + getDataInicio() + " a " + getDataFim());
        System.out.println("Total de ordens: " + ordens.size());
        System.out.println("Valor total de vendas: R$ " + totalVendas);
        System.out.println("Detalhes das ordens:");
        ordens.forEach(os -> System.out.println("  Ordem ID=" + os.getId() + ", Valor=" + os.calcularValor()));
    }

    @Override
    public String toString() {
        String ordensStr = ordens.stream()
                .map(os -> String.valueOf(os.getId()))
                .collect(Collectors.joining(", "));
        return "RelatorioVendas{" +
               "id=" + getId() +
               ", dataInicio=" + getDataInicio() +
               ", dataFim=" + getDataFim() +
               ", ordensIDs=[" + ordensStr + "]" +
               '}';
    }
}