package com.mycompany.oficina.persistence;

import com.google.gson.reflect.TypeToken;
import com.mycompany.oficina.model.Fatura;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações CRUD de Fatura em JSON.
 */
public class FaturaRepository {

    private static final String FILE_NAME = "faturas.json";
    private List<Fatura> faturas;
    private final Type TYPE = new TypeToken<List<Fatura>>(){}.getType();

    /**
     * Construtor: carrega faturas do arquivo JSON (ou lista vazia).
     */
    public FaturaRepository() {
        this.faturas = JSONUtil.loadList(FILE_NAME, TYPE);
    }

    /**
     * Retorna todas as faturas.
     */
    public List<Fatura> findAll() {
        return faturas;
    }

    /**
     * Busca uma fatura por ID.
     * @param id identificador da fatura
     * @return Optional de Fatura
     */
    public Optional<Fatura> findById(int id) {
        return faturas.stream()
                       .filter(f -> f.getId() == id)
                       .findFirst();
    }

    /**
     * Adiciona nova fatura e persiste.
     */
    public void add(Fatura fatura) {
        faturas.add(fatura);
        save();
    }

    /**
     * Atualiza uma fatura existente (por ID) e persiste.
     */
    public boolean update(Fatura fatura) {
        Optional<Fatura> opt = findById(fatura.getId());
        if (opt.isPresent()) {
            int idx = faturas.indexOf(opt.get());
            faturas.set(idx, fatura);
            save();
            return true;
        }
        return false;
    }

    /**
     * Remove fatura por ID e persiste.
     */
    public boolean remove(int id) {
        Optional<Fatura> opt = findById(id);
        if (opt.isPresent()) {
            faturas.remove(opt.get());
            save();
            return true;
        }
        return false;
    }

    /**
     * Persiste a lista de faturas em JSON.
     */
    private void save() {
        JSONUtil.saveList(FILE_NAME, faturas);
    }
}
