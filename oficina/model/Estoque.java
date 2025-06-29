// src/main/java/com/mycompany/oficina/model/Estoque.java
package com.mycompany.oficina.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

/**
 * Gerencia o estoque de produtos na oficina.
 */
public class Estoque {
    private Map<Produto, Integer> itens;

    /**
     * Construtor padrão inicializando estoque vazio.
     */
    public Estoque() {
        this.itens = new HashMap<>();
    }

    /**
     * Retorna a quantidade em estoque do produto.
     */
    public int verificarEstoque(Produto produto) {
        return itens.getOrDefault(produto, 0);
    }

    /**
     * Remove uma quantidade do estoque.
     */
    public void removerEstoque(Produto produto, int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa: " + quantidade);
        }
        int atual = verificarEstoque(produto);
        if (quantidade > atual) {
            throw new IllegalArgumentException(
                "Estoque insuficiente para produto: " + produto.getCodigo()
            );
        }
        itens.put(produto, atual - quantidade);
    }

    /**
     * Recebe itens de compra e adiciona ao estoque.
     *
     * @param itensCompra mapa de produtos e quantidades adquiridas
     */
    public void receberCompra(Map<Produto, Integer> itensCompra) {
        for (Map.Entry<Produto, Integer> entry : itensCompra.entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            if (quantidade < 0) {
                throw new IllegalArgumentException(
                    "Quantidade de compra não pode ser negativa: " + quantidade
                );
            }
            int atual = itens.getOrDefault(produto, 0);
            itens.put(produto, atual + quantidade);
        }
    }

    @Override
    public String toString() {
        return "Estoque{" +
               "itens=" + itens +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estoque)) return false;
        Estoque estoque = (Estoque) o;
        return Objects.equals(itens, estoque.itens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itens);
    }
}
