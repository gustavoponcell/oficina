// src/main/java/com/mycompany/oficina/model/Produto.java
package com.mycompany.oficina.model;

import java.util.Objects;

/**
 * Representa um produto com código, nome e preço.
 */
public class Produto {
    private int codigo;
    private String nome;
    private double preco;

    /** Construtor vazio (para desserialização). */
    public Produto() { }

    /**
     * Construtor completo.
     *
     * @param codigo Identificador único do produto.
     * @param nome   Nome do produto.
     * @param preco  Preço unitário do produto.
     */
    public Produto(int codigo, String nome, double preco) {
        this.codigo = codigo;
        this.nome   = nome;
        setPreco(preco);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo");
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo: " + preco);
        }
        this.preco = preco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        Produto that = (Produto) o;
        return codigo == that.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Produto{" +
               "codigo=" + codigo +
               ", nome='" + nome + '\'' +
               ", preco=" + preco +
               '}';
    }
}
