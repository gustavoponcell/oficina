package com.mycompany.oficina.persistence;

import com.google.gson.reflect.TypeToken;
import com.mycompany.oficina.model.Veiculo;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações CRUD de Veiculo em JSON.
 */
public class VeiculoRepository {

    private static final String FILE_NAME = "veiculos.json";
    private List<Veiculo> veiculos;
    private final Type TYPE = new TypeToken<List<Veiculo>>(){}.getType();

    /**
     * Construtor: carrega veículos do arquivo JSON (ou lista vazia).
     */
    public VeiculoRepository() {
        this.veiculos = JSONUtil.loadList(FILE_NAME, TYPE);
    }

    /**
     * Retorna todos os veículos.
     */
    public List<Veiculo> findAll() {
        return veiculos;
    }

    /**
     * Busca um veículo por ID.
     * @param id identificador do veículo
     * @return Optional de Veiculo
     */
    public Optional<Veiculo> findById(int id) {
        return veiculos.stream()
                        .filter(v -> v.getId() == id)
                        .findFirst();
    }

    /**
     * Adiciona um novo veículo e persiste.
     */
    public void add(Veiculo veiculo) {
        veiculos.add(veiculo);
        save();
    }

    /**
     * Atualiza um veículo existente (por ID) e persiste.
     * @param veiculo veículo com dados atualizados
     * @return true se atualizado; false caso não exista
     */
    public boolean update(Veiculo veiculo) {
        Optional<Veiculo> opt = findById(veiculo.getId());
        if (opt.isPresent()) {
            int idx = veiculos.indexOf(opt.get());
            veiculos.set(idx, veiculo);
            save();
            return true;
        }
        return false;
    }

    /**
     * Remove veículo por ID e persiste.
     * @param id identificador do veículo
     * @return true se removido; false caso não exista
     */
    public boolean remove(int id) {
        Optional<Veiculo> opt = findById(id);
        if (opt.isPresent()) {
            veiculos.remove(opt.get());
            save();
            return true;
        }
        return false;
    }

    /**
     * Persiste a lista de veículos em JSON.
     */
    private void save() {
        JSONUtil.saveList(FILE_NAME, veiculos);
    }
}
