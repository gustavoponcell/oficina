package com.mycompany.oficina.model;

import java.util.Map;

/**
 * Representa um fornecedor que fornece produtos ao estoque da oficina.
 */
public class Fornecedor {
    private int id;
    private String nome;
    private String telefone;

    /** Construtor vazio para desserialização. */
    public Fornecedor() { }

    /**
     * Construtor completo.
     * @param id identificador único do fornecedor
     * @param nome nome ou razão social do fornecedor
     * @param telefone contato telefônico
     */
    public Fornecedor(int id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Entrega produtos ao estoque conforme especificado.
     * @param itens mapa de produtos e quantidades a serem entregues
     */
    public void entregarProdutos(Map<Produto, Integer> itens) {
        // TODO: delegar atualização ao EstoqueService para refletir no estoque
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", telefone='" + telefone + '\'' +
               '}';
    }
}
