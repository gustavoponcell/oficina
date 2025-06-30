// src/main/java/com/mycompany/oficina/persistence/ProdutoRepository.java
package com.mycompany.oficina.persistence;

import com.google.gson.reflect.TypeToken;
import com.mycompany.oficina.model.Produto;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para CRUD de Produto, persistindo em JSON.
 */
public class ProdutoRepository {
    private static final String FILE_NAME = "produtos.json";
    private final Type listType = new TypeToken<List<Produto>>() {}.getType();
    private List<Produto> produtos;

    public ProdutoRepository() {
        try {
            produtos = JSONUtil.loadList(FILE_NAME, listType);
        } catch (Exception e) {
            produtos = new ArrayList<>();
        }
    }

    /** Adiciona um novo produto e persiste. */
    public void add(Produto p) {
        produtos.add(p);
        save();
    }

    /** Busca produto por código. */
    public Optional<Produto> findById(int codigo) {
        return produtos.stream()
                       .filter(p -> p.getCodigo() == codigo)
                       .findFirst();
    }

    /** Retorna todos os produtos (lista imutável). */
    public List<Produto> findAll() {
        return List.copyOf(produtos);
    }

    /**
     * Remove produto pelo código.
     * @return true se o produto existia e foi removido.
     */
    public boolean remove(int codigo) {
        boolean removed = produtos.removeIf(p -> p.getCodigo() == codigo);
        if (removed) save();
        return removed;
    }

    /**
     * Atualiza um produto existente (mesmo código) e persiste.
     */
    public void update(Produto p) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getCodigo() == p.getCodigo()) {
                produtos.set(i, p);
                save();
                return;
            }
        }
    }

    /** Persiste a lista de produtos no arquivo JSON. */
    private void save() {
        JSONUtil.saveList(FILE_NAME, produtos);
    }
}
