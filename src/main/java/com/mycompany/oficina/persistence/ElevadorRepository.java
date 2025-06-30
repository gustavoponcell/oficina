package com.mycompany.oficina.persistence;

import com.google.gson.reflect.TypeToken;
import com.mycompany.oficina.model.Elevador;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações CRUD de Elevador em JSON.
 */
public class ElevadorRepository {

    private static final String FILE_NAME = "elevadores.json";
    private List<Elevador> elevadores;
    private final Type TYPE = new TypeToken<List<Elevador>>() {}.getType();

    /**
     * Construtor: carrega elevadores do arquivo JSON (ou lista vazia).
     */
    public ElevadorRepository() {
        this.elevadores = JSONUtil.loadList(FILE_NAME, TYPE);
    }

    /**
     * Retorna todos os elevadores.
     */
    public List<Elevador> findAll() {
        return elevadores;
    }

    /**
     * Busca um elevador por ID.
     * @param id identificador do elevador
     * @return Optional de Elevador
     */
    public Optional<Elevador> findById(int id) {
        return elevadores.stream()
                          .filter(e -> e.getId() == id)
                          .findFirst();
    }

    /**
     * Adiciona novo elevador e persiste.
     */
    public void add(Elevador elevador) {
        elevadores.add(elevador);
        save();
    }

    /**
     * Atualiza um elevador existente (por ID) e persiste.
     * @return true se atualizado; false caso não exista
     */
    public boolean update(Elevador elevador) {
        Optional<Elevador> opt = findById(elevador.getId());
        if (opt.isPresent()) {
            int idx = elevadores.indexOf(opt.get());
            elevadores.set(idx, elevador);
            save();
            return true;
        }
        return false;
    }

    /**
     * Remove um elevador por ID e persiste.
     * @return true se removido; false caso não exista
     */
    public boolean remove(int id) {
        Optional<Elevador> opt = findById(id);
        if (opt.isPresent()) {
            elevadores.remove(opt.get());
            save();
            return true;
        }
        return false;
    }

    /**
     * Persiste a lista de elevadores em JSON.
     */
    private void save() {
        JSONUtil.saveList(FILE_NAME, elevadores);
    }
}
