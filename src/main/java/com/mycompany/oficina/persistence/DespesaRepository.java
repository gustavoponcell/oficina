package com.mycompany.oficina.persistence;

import com.google.gson.reflect.TypeToken;
import com.mycompany.oficina.model.Despesa;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações CRUD de Despesa em JSON.
 */
public class DespesaRepository {

    private static final String FILE_NAME = "despesas.json";
    private List<Despesa> despesas;
    private final Type TYPE = new TypeToken<List<Despesa>>(){}.getType();

    /**
     * Construtor: carrega despesas do arquivo JSON (ou lista vazia).
     */
    public DespesaRepository() {
        this.despesas = JSONUtil.loadList(FILE_NAME, TYPE);
    }

    /**
     * Retorna todas as despesas.
     */
    public List<Despesa> findAll() {
        return despesas;
    }

    /**
     * Busca uma despesa por ID.
     * @param id identificador da despesa
     * @return Optional de Despesa
     */
    public Optional<Despesa> findById(int id) {
        return despesas.stream()
                        .filter(d -> d.getId() == id)
                        .findFirst();
    }

    /**
     * Adiciona nova despesa e persiste.
     */
    public void add(Despesa despesa) {
        despesas.add(despesa);
        save();
    }

    /**
     * Atualiza uma despesa existente (por ID) e persiste.
     */
    public boolean update(Despesa despesa) {
        Optional<Despesa> opt = findById(despesa.getId());
        if (opt.isPresent()) {
            int idx = despesas.indexOf(opt.get());
            despesas.set(idx, despesa);
            save();
            return true;
        }
        return false;
    }

    /**
     * Remove despesa por ID e persiste.
     */
    public boolean remove(int id) {
        Optional<Despesa> opt = findById(id);
        if (opt.isPresent()) {
            despesas.remove(opt.get());
            save();
            return true;
        }
        return false;
    }

    /**
     * Persiste a lista de despesas em JSON.
     */
    private void save() {
        JSONUtil.saveList(FILE_NAME, despesas);
    }
}
