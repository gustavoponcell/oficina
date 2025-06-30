package com.mycompany.oficina.model;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import com.mycompany.oficina.persistence.AgendamentoRepository;

/**
 * Representa um agendamento de serviço para um cliente e veículo na oficina.
 */
public class Agendamento {
    private int id;
    private LocalDateTime dataHora;
    private Cliente cliente;
    private Veiculo veiculo;
    private String descricaoProblema;
    private String estadoInicial;
    private double valorRetido;
    private StatusOS status;                    // NOVO CAMPO

    public Agendamento() {
        this.status = StatusOS.RECEBIDO;        // valor padrão
    }

    public Agendamento(int id,
                       LocalDateTime dataHora,
                       Cliente cliente,
                       Veiculo veiculo,
                       String descricaoProblema,
                       String estadoInicial,
                       double valorRetido,
                       StatusOS status) {
        this.id = id;
        this.dataHora = dataHora;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.descricaoProblema = descricaoProblema;
        this.estadoInicial = estadoInicial;
        this.valorRetido = valorRetido;
        this.status = status;
    }

    // Getters e Setters existentes...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public double getValorRetido() {
        return valorRetido;
    }

    public void setValorRetido(double valorRetido) {
        this.valorRetido = valorRetido;
    }

    /*** NOVOS getter/setter para status ***/
    public StatusOS getStatus() {
        return status;
    }

    public void setStatus(StatusOS status) {
        this.status = status;
    }

    /**
     * Persiste este agendamento no repositório.
     */
    public void salvar() {
        AgendamentoRepository repo = new AgendamentoRepository();
        // Como add() retorna void, basta invocar:
        repo.add(this);
        // Se quiser capturar falhas, trate exceções lançadas por add()
    }

    /**
     * Cancela este agendamento, delegando ao repositório.
     */
    public void cancelar() {
        AgendamentoRepository repo = new AgendamentoRepository();
        if (!repo.remove(this.id)) {
            throw new RuntimeException("Falha ao cancelar agendamento com ID " + this.id);
        }
    }

    // Q16: Comparadores estáticos para Agendamento
    public static Comparator<Agendamento> comparatorPorData() {
        return Comparator.comparing(Agendamento::getDataHora);
    }

    public static Comparator<Agendamento> comparatorPorId() {
        return Comparator.comparingInt(Agendamento::getId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agendamento)) return false;
        Agendamento that = (Agendamento) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Agendamento{" +
               "id=" + id +
               ", dataHora=" + dataHora +
               ", cliente=" + cliente +
               ", veiculo=" + veiculo +
               ", status=" + status +       // mostra status também
               '}';
    }
}
