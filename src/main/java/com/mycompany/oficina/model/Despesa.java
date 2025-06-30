package com.mycompany.oficina.model;

import java.util.Date;

/**
 * Representa uma despesa operacional da oficina, como limpeza, material de uso diário, etc.
 */
public class Despesa {

    private int id;
    private String tipo;
    private String descricao;  // descrição detalhada da despesa
    private Date data;
    private double valor;

    /**
     * Construtor vazio para frameworks de desserialização.
     */
    public Despesa() {
    }

    /**
     * Construtor completo.
     * @param id          identificador único da despesa
     * @param tipo        tipo ou categoria da despesa
     * @param descricao   descrição detalhada da despesa
     * @param data        data em que a despesa ocorreu
     * @param valor       valor monetário da despesa
     */
    public Despesa(int id, String tipo, String descricao, Date data, double valor) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.data = data;
        this.valor = valor;
    }

    /** @return o identificador da despesa */
    public int getId() {
        return id;
    }

    /** @param id define o identificador da despesa */
    public void setId(int id) {
        this.id = id;
    }

    /** @return o tipo da despesa */
    public String getTipo() {
        return tipo;
    }

    /** @param tipo define a categoria da despesa */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /** @return a descrição detalhada da despesa */
    public String getDescricao() {
        return descricao;
    }

    /** @param descricao define a descrição detalhada da despesa */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /** @return a data da despesa */
    public Date getData() {
        return data;
    }

    /** @param data define a data da despesa */
    public void setData(Date data) {
        this.data = data;
    }

    /** @return o valor da despesa */
    public double getValor() {
        return valor;
    }

    /** @param valor define o valor da despesa */
    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Despesa{" +
               "id=" + id +
               ", tipo='" + tipo + '\'' +
               ", descricao='" + descricao + '\'' +
               ", data=" + data +
               ", valor=" + valor +
               '}';
    }
}
