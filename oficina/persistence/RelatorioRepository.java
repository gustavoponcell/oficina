package com.mycompany.oficina.persistence;

import com.google.gson.reflect.TypeToken;
import com.mycompany.oficina.model.Relatorio;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para persistir relatórios (abstratos e concretos) em JSON.
 * Persiste relatórios em JSON, lidando com subclasses de Relatorio através do
 * RuntimeTypeAdapter registrado em JSONUtil.
 */
public class RelatorioRepository {

    private static final String FILE_NAME = "relatorios.json";
    private final List<Relatorio> relatorios;
    private static final Type TYPE = new TypeToken<List<Relatorio>>() {}.getType();

    /**
     * Construtor: carrega relatórios do arquivo JSON (ou lista vazia).
     */
    public RelatorioRepository() {
        this.relatorios = JSONUtil.loadList(FILE_NAME, TYPE);
    }

    /**
     * Retorna todos os relatórios (não modificáveis externamente).
     */
    public List<Relatorio> findAll() {
        return Collections.unmodifiableList(relatorios);
    }

    /**
     * Busca relatório por ID.
     */
    public Optional<Relatorio> findById(int id) {
        return relatorios.stream()
                         .filter(r -> r.getId() == id)
                         .findFirst();
    }

    /**
     * Adiciona novo relatório e persiste.
     */
    public void add(Relatorio relatorio) {
        relatorios.add(relatorio);
        save();
    }

    /**
     * Atualiza um relatório existente (por ID).
     * @return true se atualizado; false caso não exista
     */
    public boolean update(Relatorio relatorio) {
        Optional<Relatorio> opt = findById(relatorio.getId());
        if (opt.isPresent()) {
            int idx = relatorios.indexOf(opt.get());
            relatorios.set(idx, relatorio);
            save();
            return true;
        }
        return false;
    }

    /**
     * Remove relatório por ID.
     * @return true se removido; false caso não exista
     */
    public boolean remove(int id) {
        Optional<Relatorio> opt = findById(id);
        if (opt.isPresent()) {
            relatorios.remove(opt.get());
            save();
            return true;
        }
        return false;
    }

    /**
     * Persiste a lista de relatórios em JSON.
     */
    private void save() {
        JSONUtil.saveList(FILE_NAME, relatorios);
    }
}
