package com.mycompany.oficina.persistence;

import com.google.gson.reflect.TypeToken;
import com.mycompany.oficina.model.OrdemServico;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações CRUD de OrdemServico em JSON.
 */
public class OrdemServicoRepository {
    private static final String FILE_NAME = "ordensServico.json";
    private List<OrdemServico> ordens;
    private final Type TYPE = new TypeToken<List<OrdemServico>>() {}.getType();

    /**
     * Construtor: carrega ordens de serviço do arquivo JSON (ou lista vazia).
     */
    public OrdemServicoRepository() {
        List<OrdemServico> loaded = JSONUtil.loadList(FILE_NAME, TYPE);
        this.ordens = (loaded != null ? loaded : new ArrayList<>());
    }

    /**
     * Retorna todas as ordens de serviço.
     */
    public List<OrdemServico> listarTodas() {
        return ordens;
    }

    /**
     * Alias para compatibilidade: retorna todas as ordens de serviço.
     */
    public List<OrdemServico> findAll() {
        return listarTodas();
    }

    /**
     * Busca uma ordem de serviço por ID.
     */
    public Optional<OrdemServico> findById(int id) {
        return ordens.stream()
                     .filter(o -> o.getId() == id)
                     .findFirst();
    }

    /**
     * Adiciona nova ordem de serviço e persiste.
     */
    public void add(OrdemServico ordem) {
        ordens.add(ordem);
        save();
    }

    /**
     * Alias para compatibilidade de nomenclatura.
     */
    public void salvar(OrdemServico ordem) {
        add(ordem);
    }

    /**
     * Atualiza ordem existente (por ID) e persiste.
     */
    public boolean update(OrdemServico ordem) {
        Optional<OrdemServico> opt = findById(ordem.getId());
        if (opt.isPresent()) {
            int idx = ordens.indexOf(opt.get());
            ordens.set(idx, ordem);
            save();
            return true;
        }
        return false;
    }

    /**
     * Remove ordem por ID e persiste.
     */
    public boolean remove(int id) {
        Optional<OrdemServico> opt = findById(id);
        if (opt.isPresent()) {
            ordens.remove(opt.get());
            save();
            return true;
        }
        return false;
    }

    /**
     * Persiste lista de ordens de serviço em JSON.
     */
    private void save() {
        JSONUtil.saveList(FILE_NAME, ordens);
    }
}