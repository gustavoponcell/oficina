package com.mycompany.oficina.model;

/**
 * Representa um item genérico (peça ou serviço) incluído em uma ordem de serviço.
 * Classe abstrata, pois cada subclasse define seu próprio cálculo de valor.
 */
public abstract class Item {

    private int codigo;
    private String descricao;

    /** Construtor vazio para frameworks de desserialização. */
    public Item() { }

    /**
     * Construtor completo.
     * @param codigo código único do item
     * @param descricao descrição do item
     */
    public Item(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    /** @return o código do item */
    public int getCodigo() {
        return codigo;
    }

    /** @param codigo define o código do item */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /** @return a descrição do item */
    public String getDescricao() {
        return descricao;
    }

    /** @param descricao define a descrição do item */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Cada subtipo deve calcular seu próprio valor (peça = preço unitário; 
     * serviço = valor horário ou fixo).
     * @return valor monetário deste item
     */
    public abstract double calcularValor();

    @Override
    public String toString() {
        return "Item{" +
               "codigo=" + codigo +
               ", descricao='" + descricao + '\'' +
               '}';
    }
}
