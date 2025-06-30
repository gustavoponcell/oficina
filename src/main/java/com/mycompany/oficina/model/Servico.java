package com.mycompany.oficina.model;

/**
 * Representa um serviço prestado pela oficina.
 * Estende Item e define valor fixo ou por hora.
 */
public class Servico extends Item {


    private double valor;

    /**
     * Construtor vazio para desserialização.
     */
    public Servico() {
        super();
    }

    /**
     * Construtor completo.
     * @param codigo        código único do serviço
     * @param descricao     descrição do serviço
     * @param valor         valor do serviço
     */
    public Servico(int codigo, String descricao, double valor) {
        super(codigo, descricao);
        this.valor = valor;
    }

    /**
     * @return valor definido para o serviço
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor define o valor do serviço
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * Calcula o valor deste item (serviço).
     * @return valor do serviço
     */
    @Override
    public double calcularValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "Servico{" +
               "codigo=" + getCodigo() +
               ", descricao='" + getDescricao() + '\'' +
               ", valor=" + valor +
               '}';
    }
}
