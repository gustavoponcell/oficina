package com.mycompany.oficina.persistence;

import com.google.gson.reflect.TypeToken;
import com.mycompany.oficina.model.Fornecedor;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações CRUD de Fornecedor em JSON.
 */
public class FornecedorRepository {

    private static final String FILE_NAME = "fornecedores.json";
    private List<Fornecedor> fornecedores;
    private final Type TYPE = new TypeToken<List<Fornecedor>>() {}.getType();

    /**
     * Construtor: carrega fornecedores do arquivo JSON (ou lista vazia).
     */
    public FornecedorRepository() {
        this.fornecedores = JSONUtil.loadList(FILE_NAME, TYPE);
    }

    /**
     * Retorna todos os fornecedores.
     */
    public List<Fornecedor> findAll() {
        return fornecedores;
    }

    /**
     * Busca um fornecedor por ID.
     * @param id identificador do fornecedor
     * @return Optional de Fornecedor
     */
    public Optional<Fornecedor> findById(int id) {
        return fornecedores.stream()
                            .filter(f -> f.getId() == id)
                            .findFirst();
    }

    /**
     * Adiciona novo fornecedor e persiste.
     */
    public void add(Fornecedor fornecedor) {
        fornecedores.add(fornecedor);
        save();
    }

    /**
     * Atualiza um fornecedor existente (por ID) e persiste.
     * @param fornecedor objeto com dados atualizados
     * @return true se atualizado; false caso não exista
     */
    public boolean update(Fornecedor fornecedor) {
        Optional<Fornecedor> opt = findById(fornecedor.getId());
        if (opt.isPresent()) {
            int idx = fornecedores.indexOf(opt.get());
            fornecedores.set(idx, fornecedor);
            save();
            return true;
        }
        return false;
    }

    /**
     * Remove um fornecedor por ID e persiste.
     * @param id identificador do fornecedor
     * @return true se removido; false caso não exista
     */
    public boolean remove(int id) {
        Optional<Fornecedor> opt = findById(id);
        if (opt.isPresent()) {
            fornecedores.remove(opt.get());
            save();
            return true;
        }
        return false;
    }

    /**
     * Persiste a lista de fornecedores em JSON.
     */
    private void save() {
        JSONUtil.saveList(FILE_NAME, fornecedores);
    }
}
