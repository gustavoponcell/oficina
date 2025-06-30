package com.mycompany.oficina.model;

import java.util.Date;

/**
 * Classe base abstrata para relatórios de período definido.
 * Subclasses implementam o método gerar() para produzir seu conteúdo.
 */
public abstract class Relatorio {

    private int id;
    private Date dataInicio;
    private Date dataFim;

    /** Construtor vazio para frameworks de desserialização. */
    public Relatorio() { }

    /**
     * Construtor completo.
     * @param id identificador do relatório
     * @param dataInicio início do período
     * @param dataFim fim do período
     */
    public Relatorio(int id, Date dataInicio, Date dataFim) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    /**
     * Método abstrato que cada relatório deve implementar para gerar seu conteúdo.
     */
    public abstract void gerar();

    @Override
    public String toString() {
        return "Relatorio{" +
               "id=" + id +
               ", dataInicio=" + dataInicio +
               ", dataFim=" + dataFim +
               '}';
    }
}
