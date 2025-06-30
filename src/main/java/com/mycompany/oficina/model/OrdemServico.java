package com.mycompany.oficina.model;

import com.mycompany.oficina.persistence.FaturaRepository;
import com.mycompany.oficina.persistence.OrdemServicoRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Representa a ordem de serviço gerada a partir de um agendamento,
 * contendo os itens (peças e serviços) e lógica de cálculos, persistência
 * de fatura e emissão de nota fiscal.
 */
public class OrdemServico {

    private int id;
    private Agendamento agendamento;
    private List<Item> itens;
    private String descricaoServico;
    private double orcamento;
    private LocalDateTime dataAbertura;

    /** Construtor vazio (desserialização). */
    public OrdemServico() {
        this.itens = new ArrayList<>();
        this.dataAbertura = LocalDateTime.now();
    }

    /**
     * Construtor padrão.
     *
     * @param id           identificador da ordem
     * @param agendamento  agendamento associado (contém cliente e veículo)
     * @param itens        lista de itens (peças/serviços)
     */
    public OrdemServico(int id, Agendamento agendamento, List<Item> itens) {
        this.id = id;
        this.agendamento = agendamento;
        this.itens = itens != null ? itens : new ArrayList<>();
        this.orcamento = 0.0;
        this.dataAbertura = LocalDateTime.now();
    }

    // ===== Getters e Setters =====

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens != null ? itens : new ArrayList<>();
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }

    public double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(double orcamento) {
        this.orcamento = orcamento;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    /** Conveniência: retorna o cliente do agendamento. */
    public Cliente getCliente() {
        return agendamento != null ? agendamento.getCliente() : null;
    }

    /** Conveniência: retorna o veículo do agendamento. */
    public Veiculo getVeiculo() {
        return agendamento != null ? agendamento.getVeiculo() : null;
    }

    /**
     * Permite associar diretamente um cliente, criando internamente um agendamento vazio.
     */
    public void setCliente(Cliente cliente) {
        if (this.agendamento == null) {
            this.agendamento = new Agendamento();
        }
        this.agendamento.setCliente(cliente);
    }

    /**
     * Permite associar diretamente um veículo, criando internamente um agendamento vazio.
     */
    public void setVeiculo(Veiculo veiculo) {
        if (this.agendamento == null) {
            this.agendamento = new Agendamento();
        }
        this.agendamento.setVeiculo(veiculo);
    }

    // ===== Lógica de Negócio =====

    /**
     * Calcula o valor total da ordem e persiste uma fatura.
     * @return soma dos valores de todos os itens
     */
    public double calcularValor() {
        double total = itens.stream()
                            .mapToDouble(Item::calcularValor)
                            .sum();

        // Persiste fatura
        FaturaRepository frepo = new FaturaRepository();
        int nextId = frepo.findAll().size() + 1;
        Fatura fatura = new Fatura();
        fatura.setId(nextId);
        fatura.setData(new Date());
        fatura.setValorTotal(total);
        fatura.setCliente(getCliente());

        frepo.add(fatura);
        fatura.salvarExtrato();

        // Atualiza orçamento desta OS
        this.orcamento = total;
        return total;
    }

    /**
     * Emite a nota fiscal em arquivo texto.
     */
    public void gerarNotaFiscal() {
        StringBuilder nota = new StringBuilder();
        nota.append("=== NOTA FISCAL ===")
            .append("\nID: ").append(id)
            .append("\nCliente: ").append(getCliente() != null ? getCliente().getNome() : "N/A")
            .append("\nVeículo: ").append(getVeiculo() != null ? getVeiculo().getPlaca() : "N/A")
            .append("\nServiço: ").append(descricaoServico != null ? descricaoServico : "N/A")
            .append("\nTotal: R$").append(orcamento);

        try (FileWriter fw = new FileWriter("nota-" + id + ".txt")) {
            fw.write(nota.toString());
        } catch (IOException e) {
            System.err.println("Erro ao gerar nota: " + e.getMessage());
        }
    }

    /**
     * Persiste esta ordem de serviço no repositório.
     */
    public void salvar() {
        OrdemServicoRepository repo = new OrdemServicoRepository();
        repo.add(this);
    }

    // ===== equals, hashCode e toString =====

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrdemServico)) return false;
        OrdemServico that = (OrdemServico) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OrdemServico{" +
               "id=" + id +
               ", cliente=" + (getCliente() != null ? getCliente().getNome() : "null") +
               ", veiculo=" + (getVeiculo() != null ? getVeiculo().getPlaca() : "null") +
               ", descricaoServico='" + descricaoServico + '\'' +
               ", itens=" + itens +
               ", orcamento=" + orcamento +
               ", dataAbertura=" + dataAbertura +
               '}';
    }
}
